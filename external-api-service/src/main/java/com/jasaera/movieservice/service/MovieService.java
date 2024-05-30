package com.jasaera.movieservice.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.jasaera.movieservice.dto.MovieDTO;
import com.jasaera.movieservice.entity.Movie;
import com.jasaera.movieservice.repository.MovieRepository;
import com.jsaera.movieservice.mapper.MovieMapper;

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
	
	public Page<MovieDTO> getByTitle(int skip, int limit, String title) {
		List<Movie> movieList = movieRepository.findByTitleContainingIgnoreCase(title);
		Page<Movie> pageMovie = new PageImpl<>(movieList);
		Page<MovieDTO> pageMovieDTO = pageMovie.map((movie) -> {
			return MovieMapper.toDTO(movie);
		});
		return pageMovieDTO;	
	}
	
	public Page<MovieDTO> getAllOrderedByPopularity(int skip, int limit) {
		List<Movie> movieList = movieRepository.findAllMoviesOrderedByPopularityDesc();
		Page<Movie> pageMovie = new PageImpl<>(movieList);
		Page<MovieDTO> pageMovieDTO = pageMovie.map((movie) -> {
			return MovieMapper.toDTO(movie);
		});
		return pageMovieDTO;	
	}
	
	public Page<MovieDTO> getTopPopularity(int skip, int limit) {
		Pageable pageable = PageRequest.of(skip, limit);
		Page<Movie> pageMovie = movieRepository.findTopMoviesOrderedByPopularityDesc(pageable);
	    return pageMovie.map(MovieMapper::toDTO);
	}
	
	public Page<MovieDTO> getAllOrderedByVotes(int skip, int limit) {
		List<Movie> movieList = movieRepository.findAllMoviesOrderedByVoteAverageDesc();
		Page<Movie> pageMovie = new PageImpl<>(movieList);
		Page<MovieDTO> pageMovieDTO = pageMovie.map((movie) -> {
			return MovieMapper.toDTO(movie);
		});
		return pageMovieDTO;	
	}
	
	public Page<MovieDTO> getTopVotes(int skip, int limit) {
		Pageable pageable = PageRequest.of(skip, limit);
		Page<Movie> pageMovie = movieRepository.findTopMoviesOrderedByVoteAverageDesc(pageable);
	    return pageMovie.map(MovieMapper::toDTO);
	}

	public int countByVoteAndGenre(Integer genreId, int minNote, int maxNote) {
		return movieRepository.countMoviesByGenreIdAndVoteAverage(genreId, minNote, maxNote);
	}
	
	public int countByYearAndGenre(Integer genreId, int year) {
		return movieRepository.countMoviesByGenreIdAndYear(genreId, year);
	}
	
}
