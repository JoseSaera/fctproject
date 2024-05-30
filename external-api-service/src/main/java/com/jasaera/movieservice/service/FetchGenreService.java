package com.jasaera.movieservice.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jasaera.movieservice.dto.ApiGenreResponseDTO;
import com.jasaera.movieservice.dto.GenreDTO;
import com.jasaera.movieservice.entity.Genre;
import com.jasaera.movieservice.repository.GenreRepository;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class FetchGenreService {
	
	@Autowired
	private GenreRepository genreRepository;
	
	@Value("${tmdb.bearer.token}")
	private String bearerToken;
	
	public String fetchAndSaveGenres() throws IOException, InterruptedException {
		OkHttpClient client = new OkHttpClient();
		ObjectMapper objectMapper = new ObjectMapper();
		
		Request request = new Request.Builder()
				.url("https://api.themoviedb.org/3/genre/movie/list?language=en-US")
				.get()
				.addHeader("accept", "application/json")
				.addHeader("Authorization", "Bearer " + bearerToken)
				.build();
		Response response = client.newCall(request).execute();
			
		if (response.isSuccessful()) {
				
			String responseBody = response.body().string();
			ApiGenreResponseDTO apiGenreResponseDTO = objectMapper.readValue(responseBody, ApiGenreResponseDTO.class);			
			List<GenreDTO> genresDTO = apiGenreResponseDTO.getGenres();
				
			for (GenreDTO genreDTO : genresDTO) {
				Genre genre = new Genre();
				genre.setId(genreDTO.getId());
				genre.setName(genreDTO.getName());
				
				genreRepository.save(genre);
			}
						
		} else {
			throw new IOException("Error al obtener los datos de películas: " + response.code() + " " + response.message());
		}
		return "Generos almacenados exitosamente.";
		
	 }
	
	
}
