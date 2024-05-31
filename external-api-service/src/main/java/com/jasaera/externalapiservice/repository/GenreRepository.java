package com.jasaera.externalapiservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jasaera.externalapiservice.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
