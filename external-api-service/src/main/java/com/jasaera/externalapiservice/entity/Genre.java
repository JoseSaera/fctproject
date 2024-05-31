package com.jasaera.externalapiservice.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Genre {

	@Id
	@Column(name = "genre_id")
	private int id;

	private String name;

	private List<Integer> moviesIds;

	public Genre() {
	}
	
	public int getId() {
		return id;
	}

	public void setId(int genreId) {
		this.id = genreId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Integer> getMovies() {
		return moviesIds;
	}

	public void setMovies(List<Integer> moviesIds) {
		this.moviesIds = moviesIds;
	}

}
