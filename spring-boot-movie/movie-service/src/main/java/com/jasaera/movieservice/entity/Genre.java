package com.jasaera.movieservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Genre {

    @Id
    @Column(name = "genre_id")
    private Long id;

    private String name;

    public Genre() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long genreId) {
        this.id = genreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
