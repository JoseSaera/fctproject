package com.jasaera.movieservice.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jasaera.movieservice.dto.ApiMovieResponseDTO;
import com.jasaera.movieservice.dto.MovieDTO;
import com.jasaera.movieservice.entity.Movie;
import com.jasaera.movieservice.mapper.MovieMapper;
import com.jasaera.movieservice.repository.MovieRepository;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class FetchMovieService {

	@Autowired
	private MovieRepository movieRepository;

	@Value("${tmdb.bearer.token}")
	private String bearerToken;
	
	@Value("${fetch.movie.min-vote-count}")
	private String minVoteCount;

	public String fetchAndSaveMovies(LocalDate minDate, LocalDate maxDate) throws IOException, InterruptedException {
		LocalDate currentDate = minDate.plusMonths(6);

		OkHttpClient client = new OkHttpClient();
		ObjectMapper objectMapper = new ObjectMapper();		

		int requestPerSecond = 35;
		long delayBetweenRequests = 1000 / requestPerSecond;

		while (currentDate.isBefore(maxDate)) {
			int page = 1;
			boolean hasNextPage = true;
			// Pages start at 1 and max 500
			while (hasNextPage & page < 500) {
				long startTime = System.currentTimeMillis();

				String releaseDateGte = minDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
				String releaseDateLte = currentDate.format(DateTimeFormatter.ISO_LOCAL_DATE);

				Request request = new Request.Builder()
						.url("https://api.themoviedb.org/3/discover/movie?page=" + page 
								+ "&primary_release_date.gte=" + releaseDateGte 
								+ "&primary_release_date.lte=" + releaseDateLte
								+ "&vote_count.gte=" + minVoteCount)
						.get().addHeader("accept", "application/json")
						.addHeader("Authorization", "Bearer " + bearerToken).build();
				Response response = client.newCall(request).execute();

				if (response.isSuccessful()) {

					String responseBody = response.body().string();
					ApiMovieResponseDTO apiMovieResponseDTO = objectMapper.readValue(responseBody,
							ApiMovieResponseDTO.class);
					List<MovieDTO> moviesDTO = apiMovieResponseDTO.getResults();

					for (MovieDTO movieDTO : moviesDTO) {

						Movie movie = MovieMapper.toEntity(movieDTO);
						if (movie.getReleaseDate() != null) {
							movieRepository.save(movie);
						}
					}

					response.close();

					long elapsedTime = System.currentTimeMillis() - startTime;
					long waitTime = delayBetweenRequests - elapsedTime;
					if (waitTime > 0) {
						Thread.sleep(waitTime);
					}

					hasNextPage = page < apiMovieResponseDTO.getTotalPages();
					page++;

				} else {

					throw new IOException(response.code() + " " + response.message());
				}

				client.dispatcher().executorService().shutdown();
				client.connectionPool().evictAll();

			}
			minDate = minDate.plusMonths(6);
			currentDate = currentDate.plusMonths(6);
			System.out.println(currentDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
		}

		return "Peliculas almacenadas exitosamente.";

	}

}
