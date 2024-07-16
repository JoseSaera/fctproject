package com.jasaera.tests;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jasaera.movieservice.MovieServiceApplication;
import com.jasaera.movieservice.controller.MovieController;
import com.jasaera.movieservice.dto.ExtendedMovieDTO;
import com.jasaera.movieservice.dto.MovieCountByYear;
import com.jasaera.movieservice.entity.Movie;
import com.jasaera.movieservice.repository.MovieRepository;

import jakarta.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MovieServiceApplication.class)
@Transactional
@ActiveProfiles("test")
public class MovieControllerTest {
	
	@Autowired 
	private MovieController movieController;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@BeforeEach
	public void setUp() {
		Movie movie1 = new Movie();
		movie1.setId(1L);
		movie1.setGenreIds(List.of(1L));
		movie1.setTitle("Example 1");
		movie1.setReleaseDate(Date.valueOf("2000-01-01"));
		movie1.setVoteAverage(7.3);
		movie1.setPopularity(100);
		movie1.setVoteCount(5L);
		
		Movie movie2 = new Movie();
		movie2.setId(2L);
		movie2.setGenreIds(List.of(1L, 2L));
		movie2.setTitle("Example 2");
		movie2.setReleaseDate(Date.valueOf("2000-12-31"));
		movie2.setVoteAverage(7);
		movie2.setPopularity(300);
		movie2.setVoteCount(3L);
		
		Movie movie3 = new Movie();
		movie3.setId(3L);
		movie3.setGenreIds(List.of(1L, 2L, 3L));
		movie3.setTitle("Another 1");
		movie3.setReleaseDate(Date.valueOf("2010-01-01"));
		movie3.setVoteAverage(9);
		movie3.setPopularity(200);
		movie3.setVoteCount(10L);
		
		movieRepository.save(movie1);
		movieRepository.save(movie2);
		movieRepository.save(movie3);
	}
	
	@Test
	public void testGetMovies() {
		ResponseEntity<Page<ExtendedMovieDTO>> response = movieController.getMovies(0, 10);
		assert response.getBody().getNumberOfElements() == 3;
	}
	
	@Test
	public void testGetMoviesByTitle() {
		ResponseEntity<Page<ExtendedMovieDTO>> response = movieController.getMoviesByTitle(0, 10, "exam");
		assert response.getBody().getNumberOfElements() == 2;
	}
	
	@Test
	public void testGetCountRatingByGenre() {
		ResponseEntity<ArrayList<Integer>> response = movieController.getCountRatingByGenre(1);
		assert response.getBody().get(7) == 2;
		assert response.getBody().get(9) == 1;
	}
	
	@Test
	public void testGetCountYearByGenre() {
		ResponseEntity<List<MovieCountByYear>> response = movieController.getCountYearByGenre(1);
		MovieCountByYear count1 = response.getBody().get(0);
		assert count1.getReleaseYear() == 2000;
		assert count1.getMovieCount() == 2;
		MovieCountByYear count2 = response.getBody().get(1);
		assert count2.getReleaseYear() == 2010;
		assert count2.getMovieCount() == 1;
	}
	
	@Test
	public void testGetYearRange() {
		ResponseEntity<Map<String, Integer>> response = movieController.getYearRange();
		assert response.getBody().get("minYear") == 2000;
		assert response.getBody().get("maxYear") == 2010;
	}
	
}
