package com.jsaera.movieservice.mapper;

import java.util.ArrayList;
import java.util.List;

import com.jasaera.movieservice.dto.GenreDTO;
import com.jasaera.movieservice.dto.MovieDTO;
import com.jasaera.movieservice.entity.Genre;
import com.jasaera.movieservice.entity.Movie;

public class GenreMapper {
	
	public static Genre toEntity(GenreDTO genreDTO) {
		Genre genre = new Genre();
		genre.setId(genreDTO.getId());
		genre.setName(genreDTO.getName());
		return genre;
	}
	
	public static GenreDTO toDTO(Genre genre) {
		GenreDTO genreDTO = new GenreDTO();
		genreDTO.setId(genre.getId());
		genreDTO.setName(genre.getName());
		return genreDTO;
	}
	
}
