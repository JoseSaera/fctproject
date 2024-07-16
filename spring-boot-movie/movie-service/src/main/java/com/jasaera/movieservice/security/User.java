package com.jasaera.movieservice.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(nullable = false)
	String name;
	
	@Column(nullable = false, unique = true)
	String username;
	String password;

	@ElementCollection
	@CollectionTable(name = "user_see_later", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "movie_id")
	private Set<Long> seeLater;

	@ElementCollection
	@CollectionTable(name = "user_favorites", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "movie_id")
	private Set<Long> favorites;
	
	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false)
	private Role role;;

    public User() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	@PrePersist
	protected void onPrePersist() {
		if (this.role == null) {
			this.role = Role.USER;
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
