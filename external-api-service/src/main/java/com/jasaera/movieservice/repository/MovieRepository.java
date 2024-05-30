package com.jasaera.movieservice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jasaera.movieservice.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
	
	List<Movie> findByTitleContainingIgnoreCase(String title);
	
	@Query("SELECT m FROM Movie m ORDER BY m.popularity DESC")
	List<Movie> findAllMoviesOrderedByPopularityDesc();

	@Query("SELECT m FROM Movie m ORDER BY m.popularity DESC")
	Page<Movie> findTopMoviesOrderedByPopularityDesc(Pageable pageable);

	@Query("SELECT m FROM Movie m ORDER BY m.voteAverage DESC")
	List<Movie> findAllMoviesOrderedByVoteAverageDesc();

	@Query("SELECT m FROM Movie m ORDER BY m.voteAverage DESC")
	Page<Movie> findTopMoviesOrderedByVoteAverageDesc(Pageable pageable);
	

    @Query("SELECT COUNT(m) FROM Movie m WHERE :genreId MEMBER OF m.genreIds AND m.voteAverage >= :minVoteAverage AND m.voteAverage <= :maxVoteAverage")
    int countMoviesByGenreIdAndVoteAverage(@Param("genreId") Integer genreId, @Param("minVoteAverage") int minNote, @Param("maxVoteAverage") int maxNote);
	
    @Query("SELECT COUNT(m) FROM Movie m WHERE :genreId MEMBER OF m.genreIds AND YEAR(m.releaseDate) = :year")
	int countMoviesByGenreIdAndYear(@Param("genreId") Integer genreId, @Param("year") int year);

}
