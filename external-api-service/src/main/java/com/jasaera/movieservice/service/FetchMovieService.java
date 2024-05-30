package com.jasaera.movieservice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jasaera.movieservice.dto.ApiMovieResponseDTO;
import com.jasaera.movieservice.dto.MovieDTO;
import com.jasaera.movieservice.entity.Genre;
import com.jasaera.movieservice.entity.Movie;
import com.jasaera.movieservice.repository.GenreRepository;
import com.jasaera.movieservice.repository.MovieRepository;
import com.jsaera.movieservice.mapper.MovieMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class FetchMovieService {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private GenreRepository genreRepository;
	
	@Value("${tmdb.bearer.token}")
	private String bearerToken;
	
	public String fetchAndSaveMovies(Integer year) throws IOException, InterruptedException {
		OkHttpClient client = new OkHttpClient();
		ObjectMapper objectMapper = new ObjectMapper();
		System.out.println(year);
		int page = 1;
		boolean hasNextPage = true;

		int requestPerSecond = 25;
		long delayBetweenRequests = 1000 / requestPerSecond;
		
		while (hasNextPage) {
			long startTime = System.currentTimeMillis();
			
			Request request = new Request.Builder()
					  .url("https://api.themoviedb.org/3/discover/movie?language=en-US&page=" + page + "&sort_by=popularity.desc&year=" + year)
					  .get()
					  .addHeader("accept", "application/json")
					  .addHeader("Authorization", "Bearer "	+ bearerToken)
					  .build();
			Response response = client.newCall(request).execute();
			
			if (response.isSuccessful()) {
				
				String responseBody = response.body().string();
				ApiMovieResponseDTO apiMovieResponseDTO = objectMapper.readValue(responseBody, ApiMovieResponseDTO.class);			
				List<MovieDTO> moviesDTO = apiMovieResponseDTO.getResults();
				
				for (MovieDTO movieDTO : moviesDTO) {

					Movie movie = MovieMapper.toEntity(movieDTO);				
					movieRepository.save(movie);
					
				}
				
				//Incrementar el numero de pagina si no es la última
				hasNextPage = page < apiMovieResponseDTO.getTotalPages();
				page++;
				//Comprobar si hay que esperar
				long elapsedTime = System.currentTimeMillis() - startTime;
				long waitTime = delayBetweenRequests - elapsedTime;
				if (waitTime > 0) {
					Thread.sleep(waitTime);
				}
				
			} else {
			        throw new IOException("Error al obtener los datos de películas: " + response.code() + " " + response.message());
			}
			
		}
		return "Peliculas almacenadas exitosamente.";
		
	 }
	
	
}
