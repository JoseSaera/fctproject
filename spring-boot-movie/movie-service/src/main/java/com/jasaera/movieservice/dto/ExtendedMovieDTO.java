package com.jasaera.movieservice.dto;

import java.util.List;

public class ExtendedMovieDTO extends MovieDTO {
	
	private List<GenreDTO> genresDTO;
	
	public ExtendedMovieDTO() {
		super();
	}

	public List<GenreDTO> getGenresDTO() {
		return genresDTO;
	}

	public void setGenresDTO(List<GenreDTO> genreDTO) {
		this.genresDTO = genreDTO;
	}
	
	
}
