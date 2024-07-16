package com.jasaera.movieservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jasaera.movieservice.entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
	List<Genre> findAllByNameIn(List<String> genreNames);
}
