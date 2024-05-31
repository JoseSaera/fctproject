package com.jasaera.externalapiservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jasaera.externalapiservice.service.FetchGenreService;
import com.jasaera.externalapiservice.service.FetchMovieService;

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
    public ResponseEntity<String> getMovies(@RequestParam(required = false) Integer year) {
    	
        try {
        	return ResponseEntity.ok(fetchMovieService.fetchAndSaveMovies(year));    
        } catch (Exception e) {
        	System.out.println("Error al obtener los datos de películas: " + e.getMessage());
        }

		return null;
    }
	

}
