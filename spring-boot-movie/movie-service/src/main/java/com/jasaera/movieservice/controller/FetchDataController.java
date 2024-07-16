package com.jasaera.movieservice.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jasaera.movieservice.service.FetchGenreService;
import com.jasaera.movieservice.service.FetchMovieService;

@RestController
@RequestMapping("/fetch-data")
public class FetchDataController {
	
	@Autowired
    private FetchGenreService fetchGenreService;
	
    @Autowired
    private FetchMovieService fetchMovieService;
    
    @GetMapping("/genres")
    public ResponseEntity<String> getGenres() {

        try {
        	return ResponseEntity.ok(fetchGenreService.fetchAndSaveGenres());    
        } catch (Exception e) {
        	System.out.println("Error al obtener los datos de películas: " + e.getMessage());
        }

		return null;
    }

    @GetMapping("/movies")
    public ResponseEntity<String> getMovies(
    		@RequestParam(required = false, defaultValue = "1900-01-01")
    		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate minDate,
    		@RequestParam(required = false, defaultValue = "2030-01-01")
    		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate maxDate) {
    	
        try {
        	return ResponseEntity.ok(fetchMovieService.fetchAndSaveMovies(minDate, maxDate));    
        } catch (Exception e) {
        	System.out.println("Error al obtener los datos de películas: " + e.getMessage());
        }

		return null;
    }

}
