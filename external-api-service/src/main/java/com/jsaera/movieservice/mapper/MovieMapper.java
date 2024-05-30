package com.jsaera.movieservice.mapper;

import java.util.ArrayList;
import java.util.List;

import com.jasaera.movieservice.dto.MovieDTO;
import com.jasaera.movieservice.entity.Genre;
import com.jasaera.movieservice.entity.Movie;

public class MovieMapper {
	
	public static Movie toEntity(MovieDTO movieDTO) {
		Movie movie = new Movie();
		movie.setId(movieDTO.getId());
		movie.setOriginalLanguage(movieDTO.getOriginalLanguage());
		movie.setVoteAverage(movieDTO.getVoteAverage());
		movie.setOriginalTitle(movieDTO.getOriginalTitle());
		movie.setBackdropPath(movieDTO.getBackdropPath());
		movie.setPopularity(movieDTO.getPopularity());
		movie.setTitle(movieDTO.getTitle());
		movie.setOverview(movieDTO.getOverview());
		movie.setAdult(movieDTO.isAdult());
		movie.setReleaseDate(movieDTO.getReleaseDate());
		movie.setVideo(movieDTO.isVideo());
		movie.setVoteCount(movieDTO.getVoteCount());
		movie.setGenreIds(movieDTO.getGenreIds());
		return movie;
	}
	
	public static MovieDTO toDTO(Movie movie) {
		MovieDTO movieDTO = new MovieDTO();
		movieDTO.setId(movie.getId());
		movieDTO.setOriginalLanguage(movie.getOriginalLanguage());
		movieDTO.setVoteAverage(movie.getVoteAverage());
		movieDTO.setOriginalTitle(movie.getOriginalTitle());
		movieDTO.setBackdropPath(movie.getBackdropPath());
		movieDTO.setPopularity(movie.getPopularity());
		movieDTO.setTitle(movie.getTitle());
		movieDTO.setOverview(movie.getOverview());
		movieDTO.setAdult(movie.isAdult());
		movieDTO.setReleaseDate(movie.getReleaseDate());
		movieDTO.setVideo(movie.isVideo());
		movieDTO.setVoteCount(movie.getVoteCount());
		movieDTO.setGenreIds(movie.getGenreIds());
		return movieDTO;
		
	}
}
