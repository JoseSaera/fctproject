package com.jsaera.externalapiservice.mapper;

import java.util.ArrayList;
import java.util.List;

import com.jasaera.externalapiservice.dto.GenreDTO;
import com.jasaera.externalapiservice.dto.MovieDTO;
import com.jasaera.externalapiservice.entity.Genre;
import com.jasaera.externalapiservice.entity.Movie;

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
