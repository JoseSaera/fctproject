package com.jasaera.movieservice.dto;

import java.util.List;

public class ApiGenreResponseDTO {

	private List<GenreDTO> genres;

	public List<GenreDTO> getGenres() {
		return genres;
	}

	public void setGenres(List<GenreDTO> genres) {
		this.genres = genres;
	}

}
