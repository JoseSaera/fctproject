package com.jasaera.externalapiservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jasaera.externalapiservice.dto.MovieDTO;
import com.jasaera.externalapiservice.entity.Movie;
import com.jasaera.externalapiservice.service.FetchGenreService;
import com.jasaera.externalapiservice.service.FetchMovieService;
import com.jasaera.externalapiservice.service.MovieService;

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
        }
        return null;
    }
    
    @GetMapping("/search-title")
    public ResponseEntity<Page<MovieDTO>> getMoviesByName(@RequestParam(defaultValue="0") int skip, @RequestParam(defaultValue="20") int limit,
    		@RequestParam(required=false) String title) {    
        try {
        	return ResponseEntity.ok(movieService.getByName(skip, limit, title));
        } catch (Exception e) {
        	System.out.println("Error al obtener los datos de películas: " + e.getMessage());
        }
        return null;
    }
}
