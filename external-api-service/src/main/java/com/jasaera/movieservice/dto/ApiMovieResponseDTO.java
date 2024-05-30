package com.jasaera.movieservice.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiMovieResponseDTO {

	private int page;
	private List<MovieDTO> results;

	@JsonProperty("total_pages")
	private int totalPages;

	@JsonProperty("total_results")
	private int totalResults;

	private ApiMovieResponseDTO() {
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<MovieDTO> getResults() {
		return results;
	}

	public void setResults(List<MovieDTO> results) {
		this.results = results;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}

}
