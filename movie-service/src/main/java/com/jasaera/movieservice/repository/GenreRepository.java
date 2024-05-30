package com.jasaera.movieservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jasaera.movieservice.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
