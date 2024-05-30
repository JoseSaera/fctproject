package com.jasaera.movieservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jasaera.movieservice.dto.MovieDTO;
import com.jasaera.movieservice.entity.Movie;
import com.jasaera.movieservice.service.FetchGenreService;
import com.jasaera.movieservice.service.FetchMovieService;
import com.jasaera.movieservice.service.MovieService;

@RestController
@RequestMapping("/movies")
public class MovieController {
	
	@Autowired
    private MovieService movieService;

    @GetMapping("")
    public ResponseEntity<Page<MovieDTO>> getMovies(@RequestParam(defaultValue="0") int skip, @RequestParam(defaultValue="20") int limit) {    
        try {
        	return ResponseEntity.ok(movieService.getAll(skip, limit));
        } catch (Exception e) {
        	System.out.println("Error al obtener los datos de películas: " + e.getMessage());
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/search-title")
    public ResponseEntity<Page<MovieDTO>> getMoviesByTitle(@RequestParam(defaultValue="0") int skip, @RequestParam(defaultValue="20") int limit,
    		@RequestParam(defaultValue="") String title) {    
        try {
        	return ResponseEntity.ok(movieService.getByTitle(skip, limit, title));
        } catch (Exception e) {
        	System.out.println("Error al obtener los datos de películas: " + e.getMessage());
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/popularity/all-time")
    public ResponseEntity<Page<MovieDTO>> getMoviesOrderedByPopularity(@RequestParam(defaultValue="0") int skip, @RequestParam(defaultValue="20") int limit) {    
        try {
        	return ResponseEntity.ok(movieService.getAllOrderedByPopularity(skip, limit));
        } catch (Exception e) {
        	System.out.println("Error al obtener los datos de películas: " + e.getMessage());
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/popularity/top")
    public ResponseEntity<Page<MovieDTO>> getTopPopularMovie(@RequestParam(defaultValue="0") int skip, @RequestParam(defaultValue="10") int limit) {    
        try {
        	return ResponseEntity.ok(movieService.getTopPopularity(skip, limit));
        } catch (Exception e) {
        	System.out.println("Error al obtener los datos de películas: " + e.getMessage());
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/votes/all-time")
    public ResponseEntity<Page<MovieDTO>> getMoviesOrderedByVotes(@RequestParam(defaultValue="0") int skip, @RequestParam(defaultValue="20") int limit) {    
        try {
        	return ResponseEntity.ok(movieService.getAllOrderedByPopularity(skip, limit));
        } catch (Exception e) {
        	System.out.println("Error al obtener los datos de películas: " + e.getMessage());
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/votes/top")
    public ResponseEntity<Page<MovieDTO>> getTopVotedMovie(@RequestParam(defaultValue="0") int skip, @RequestParam(defaultValue="10") int limit) {    
        try {
        	return ResponseEntity.ok(movieService.getTopPopularity(skip, limit));
        } catch (Exception e) {
        	System.out.println("Error al obtener los datos de películas: " + e.getMessage());
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/count/note/min/{min}/max/{max}/genreId")
    public ResponseEntity<Integer> getCountVoteByGenre(@RequestParam Integer id, @PathVariable int min, @PathVariable int max) {
        try {
        	return ResponseEntity.ok(movieService.countByVoteAndGenre(id, min, max));
        } catch (Exception e) {
        	System.out.println("Error al obtener los datos de películas: " + e.getMessage());
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    	  													
    @GetMapping("/count/year/{year}/genreId")
    public ResponseEntity<Integer> getCountYearByGenre(@RequestParam Integer id, @PathVariable int year) {
        try {
        	return ResponseEntity.ok(movieService.countByYearAndGenre(id, year));
        } catch (Exception e) {
        	System.out.println("Error al obtener los datos de películas: " + e.getMessage());
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }	
    }
    

}
