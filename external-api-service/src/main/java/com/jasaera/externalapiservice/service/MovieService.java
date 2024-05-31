package com.jasaera.externalapiservice.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.jasaera.externalapiservice.dto.MovieDTO;
import com.jasaera.externalapiservice.entity.Movie;

import com.jasaera.externalapiservice.repository.MovieRepository;
import com.jsaera.externalapiservice.mapper.MovieMapper;

@Service
public class MovieService {
	
	@Autowired
	private MovieRepository movieRepository;
	
	public Page<MovieDTO> getAll(int skip, int limit) {
		Page<Movie> pageMovie = movieRepository.findAll(PageRequest.of(skip, limit));
		Page<MovieDTO> pageMovieDTO = pageMovie.map((movie) -> {
			return MovieMapper.toDTO(movie);
		});
		return pageMovieDTO;
	}
	
	public Page<MovieDTO> getByName(int skip, int limit, String title) {
		List<Movie> movieList = movieRepository.findByTitleContainingIgnoreCase(title);
		Page<Movie> pageMovie = new PageImpl<>(movieList);
		Page<MovieDTO> pageMovieDTO = pageMovie.map((movie) -> {
			return MovieMapper.toDTO(movie);
		});
		return pageMovieDTO;	
	}
	
	

}
