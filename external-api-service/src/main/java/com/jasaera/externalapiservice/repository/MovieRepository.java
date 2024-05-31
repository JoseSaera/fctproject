package com.jasaera.externalapiservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jasaera.externalapiservice.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
	List<Movie> findByTitleContainingIgnoreCase(String title);
}
