package com.jasaera.movieservice.security;

import java.util.Set;

public class AuthenticationResponse {
	private Long id;
	private String token;
	private Set<Long> seeLater;
	private Set<Long> favorites;

	public AuthenticationResponse(Long id, String token, Set<Long> seeLater, Set<Long> favorites) {
		this.id = id;
		this.token = token;
		this.seeLater = seeLater;
		this.favorites = favorites;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Set<Long> getSeeLater() {
		return seeLater;
	}

	public void setSeeLater(Set<Long> seeLater) {
		this.seeLater = seeLater;
	}

	public Set<Long> getFavorites() {
		return favorites;
	}

	public void setFavorites(Set<Long> favorites) {
		this.favorites = favorites;
	}

}
