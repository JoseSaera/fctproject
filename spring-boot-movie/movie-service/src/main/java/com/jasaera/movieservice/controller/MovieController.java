package com.jasaera.movieservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jasaera.movieservice.dto.ExtendedMovieDTO;
import com.jasaera.movieservice.dto.MovieCountByYear;
import com.jasaera.movieservice.service.MovieService;

@RestController
@RequestMapping("/movies")
public class MovieController {

	@Autowired
	private MovieService movieService;

	@GetMapping("")
	public ResponseEntity<Page<ExtendedMovieDTO>> getMovies(@RequestParam(defaultValue = "0") int skip,
			@RequestParam(defaultValue = "20") int limit) {
		try {
			return ResponseEntity.ok(movieService.getAll(skip, limit));
		} catch (Exception e) {
			System.out.println("Error al obtener los datos de películas: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/search/title")
	public ResponseEntity<Page<ExtendedMovieDTO>> getMoviesByTitle(@RequestParam(defaultValue = "0") int skip,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "") String title) {
		try {
			return ResponseEntity.ok(movieService.getByTitle(skip, limit, title));
		} catch (Exception e) {
			System.out.println("Error al obtener los datos de películas: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/search/filters")
	public ResponseEntity<Page<ExtendedMovieDTO>> getMoviesByFilters(@RequestParam(defaultValue = "0") int skip,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "") String title,
			@RequestParam(defaultValue = "") List<Long> genreIds, @RequestParam(defaultValue = "1900") Integer minYear,
			@RequestParam(defaultValue = "2050") Integer maxYear) {

		try {
			return ResponseEntity.ok(movieService.getFilteredMovies(skip, limit, title, genreIds, minYear, maxYear));
		} catch (Exception e) {
			System.out.println("Error al obtener los datos de películas: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/popularity/top")
	public ResponseEntity<Page<ExtendedMovieDTO>> getTopPopularMovie(@RequestParam(defaultValue = "0") int skip,
			@RequestParam(defaultValue = "10") int limit) {
		try {
			return ResponseEntity.ok(movieService.getTopPopularity(skip, limit));
		} catch (Exception e) {
			System.out.println("Error al obtener los datos de películas: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/rating/top")
	public ResponseEntity<Page<ExtendedMovieDTO>> getTopRatingMovie(@RequestParam(defaultValue = "0") int skip,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1000") Long minVoteCount) {
		try {
			return ResponseEntity.ok(movieService.getTopRating(skip, limit, minVoteCount));
		} catch (Exception e) {
			System.out.println("Error al obtener los datos de películas: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/count/genreId-rating")
	public ResponseEntity<ArrayList<Integer>> getCountRatingByGenre(@RequestParam Integer id) {
		try {
			return ResponseEntity.ok(movieService.countByRatingAndGenre(id));
		} catch (Exception e) {
			System.out.println("Error al obtener los datos de películas: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/count/genreId-years")
	public ResponseEntity<List<MovieCountByYear>> getCountYearByGenre(@RequestParam Integer id) {
		try {
			return ResponseEntity.ok(movieService.countByGenreOrderByYear(id));
		} catch (Exception e) {
			System.out.println("Error al obtener los datos de películas: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/year-range")
	public ResponseEntity<Map<String, Integer>> getYearRange() {
		try {
			return ResponseEntity.ok(movieService.getMinMaxYear());
		} catch (Exception e) {
			System.out.println("Error al obtener los datos de películas: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("/average/genreId")
	public ResponseEntity<Float> getAverageByGenre(@RequestParam Integer id) {
		try {
			return ResponseEntity.ok(movieService.getRatingAverageByGenreId(id));
		} catch(Exception e) {
			System.out.println("Error al obtener los datos de películas: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
