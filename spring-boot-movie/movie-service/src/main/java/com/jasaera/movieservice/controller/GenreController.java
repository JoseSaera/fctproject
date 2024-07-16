package com.jasaera.movieservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jasaera.movieservice.dto.GenreDTO;
import com.jasaera.movieservice.service.GenreService;

@RestController
@RequestMapping("/genres")
public class GenreController {
	
	@Autowired
	private GenreService genreService;
	
	@GetMapping("")
    public ResponseEntity<List<GenreDTO>> getGenres() {    
        try {
        	return ResponseEntity.ok(genreService.getAll());
        } catch (Exception e) {
        	System.out.println("Error al obtener los datos de géneros: " + e.getMessage());
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
	
	@GetMapping("/names-by-ids")
    public ResponseEntity<List<String>> getGenreNames(@RequestParam(defaultValue="") List<Long> genreIds) {    
        try {
        	return ResponseEntity.ok(genreService.getGenreNamesByIds(genreIds));
        } catch (Exception e) {
        	System.out.println("Error al obtener los datos de películas: " + e.getMessage());
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
	
	@GetMapping("/ids-by-names")
    public ResponseEntity<List<Long>> getGenreIds(@RequestParam(defaultValue="") List<String> genreNames) {    
        try {
        	return ResponseEntity.ok(genreService.getGenreIdsByNames(genreNames));
        } catch (Exception e) {
        	System.out.println("Error al obtener los datos de películas: " + e.getMessage());
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
