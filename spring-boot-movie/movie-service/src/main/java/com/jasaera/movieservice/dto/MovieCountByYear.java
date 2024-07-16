package com.jasaera.movieservice.dto;

public class MovieCountByYear {
	private int releaseYear;
	private Long movieCount;

	public MovieCountByYear(int releaseYear, Long movieCount) {
		super();
		this.releaseYear = releaseYear;
		this.movieCount = movieCount;
	}

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public Long getMovieCount() {
		return movieCount;
	}

	public void setMovieCount(Long movieCount) {
		this.movieCount = movieCount;
	}

}
