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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jasaera.movieservice.MovieServiceApplication;
import com.jasaera.movieservice.dto.ExtendedMovieDTO;
import com.jasaera.movieservice.dto.MovieCountByYear;
import com.jasaera.movieservice.entity.Movie;
import com.jasaera.movieservice.repository.MovieRepository;
import com.jasaera.movieservice.service.MovieService;

import jakarta.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MovieServiceApplication.class)
@Transactional
@ActiveProfiles("test")
public class MovieServiceTest {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private MovieService movieService;
	
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
	public void testGetAll() {
		Page<ExtendedMovieDTO> moviePage = movieService.getAll(0, 10);
		assert moviePage.getTotalElements() == 3;
		
		moviePage = movieService.getAll(0, 1);
		Long id1 = moviePage.getContent().getFirst().getId();	
		moviePage = movieService.getAll(1, 1);
		Long id2 = moviePage.getContent().getFirst().getId();	
		assert id1 != id2;
	}
	
	@Test
	public void testGetByTitle() {
		Page<ExtendedMovieDTO> moviePage = movieService.getByTitle(0, 10, "exam");
		assert moviePage.getTotalElements() == 2;
		
		moviePage = movieService.getByTitle(0, 10, "other");
		assert moviePage.getTotalElements() == 1;
		
		moviePage = movieService.getByTitle(0, 1, "exam");
		Long id1 = moviePage.getContent().getFirst().getId();	
		moviePage = movieService.getByTitle(1, 1, "exam");
		Long id2 = moviePage.getContent().getFirst().getId();	
		assert id1 != id2;
	}
	
	@Test
	public void testCountByRatingAndGenre() {
		ArrayList<Integer> ratingList = movieService.countByRatingAndGenre(1);		
		for (int i=0; i<10; i++) {
			if(i != 7 && i != 9) {
				assert ratingList.get(i) == 0;
			}
		}
		assert ratingList.get(7) == 2;
		assert ratingList.get(9) == 1;
		
		ratingList = movieService.countByRatingAndGenre(3);
		for (int i=0; i<10; i++) {
			if(i != 9) {
				assert ratingList.get(i) == 0;
			}
		}
		assert ratingList.get(9) == 1;
	}
	
	@Test
	public void testcountByGenreOrderByYear() {
		List<MovieCountByYear> movieCount = movieService.countByGenreOrderByYear(1);		
		assert movieCount.size() == 2;
		assert movieCount.get(0).getReleaseYear() == 2000;
		assert movieCount.get(0).getMovieCount() == 2L;
		assert movieCount.get(1).getReleaseYear() == 2010;
		assert movieCount.get(1).getMovieCount() == 1L;
	}
	
	@Test
	public void testGetMinMaxYear() {
		Map<String, Integer> result = movieService.getMinMaxYear();
		assert result.get("minYear") == 2000;
		assert result.get("maxYear") == 2010;
	}
	
	@Test
	public void testGetTopPopularity() {
		Page<ExtendedMovieDTO> moviePage = movieService.getTopPopularity(0, 10);
		assert moviePage.getContent().size() == 3;
		assert moviePage.getContent().get(0).getId() == 2;
		assert moviePage.getContent().get(1).getId() == 3;
		assert moviePage.getContent().get(2).getId() == 1;
	}
	
	@Test
	public void testGetTopRating() {
		Page<ExtendedMovieDTO> moviePage = movieService.getTopRating(0, 10, 0L);
		assert moviePage.getContent().get(0).getId() == 3;
		assert moviePage.getContent().get(1).getId() == 1;
		assert moviePage.getContent().get(2).getId() == 2;
		
		moviePage = movieService.getTopRating(0, 10, 5L);
		assert moviePage.getContent().size() == 2;
	}
}
