package com.jasaera.movieservice.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jasaera.movieservice.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
	
	Page<Movie> findAll(Pageable pageable);
	
	List<Movie> findByIdIn(Set<Long> ids);
	
	@Query("SELECT m FROM Movie m WHERE " +
			"LOWER(m.title) LIKE CONCAT('%', LOWER(:title), '%') " +
			"ORDER BY LOCATE(LOWER(:title), LOWER(m.title)), m.title ASC")
	Page<Movie> findByTitleCustom(String title, Pageable pageable);
	
	@Query("SELECT m FROM Movie m WHERE " +
	        "LOWER(m.title) LIKE CONCAT('%', LOWER(:title), '%') " +
	        "AND YEAR(m.releaseDate) BETWEEN :minYear AND :maxYear " +
	        "AND EXISTS (SELECT 1 FROM Movie m2 JOIN m2.genreIds g WHERE m2 = m AND g IN :genreIds) " +
	        "ORDER BY LOCATE(LOWER(:title), LOWER(m.title)), m.title ASC")
	Page<Movie> findWithFilters(@Param("title") String title,
								@Param("genreIds") List<Long> genreIds,
								@Param("minYear") Integer minYear,
								@Param("maxYear") Integer maxYear,
								Pageable pageable);
	
	@Query("SELECT m FROM Movie m ORDER BY m.popularity DESC")
	Page<Movie> findTopMoviesOrderedByPopularityDesc(Pageable pageable);

	@Query("SELECT m FROM Movie m WHERE m.voteCount >= :minVoteCount ORDER BY m.voteAverage DESC")
	Page<Movie> findTopMoviesOrderedByVoteAverageDesc(Pageable pageable, @Param("minVoteCount") Long minVoteCount);
	
    @Query("SELECT COUNT(m) FROM Movie m WHERE " +
    		"m.voteAverage >= :minVoteAverage AND m.voteAverage < :maxVoteAverage " +
    		"AND EXISTS (SELECT 1 FROM Movie m2 JOIN m2.genreIds g WHERE m2 = m AND g IN :genreId)")
    int countMoviesByGenreIdAndVoteAverage(@Param("genreId") Integer genreId, @Param("minVoteAverage") int minNote, @Param("maxVoteAverage") int maxNote);
	
    @Query("SELECT YEAR(m.releaseDate), COUNT(m) FROM Movie m WHERE " +
    		"EXISTS (SELECT 1 FROM Movie m2 JOIN m2.genreIds g WHERE m2 = m AND g IN :genreId) " +
    		"GROUP BY YEAR(m.releaseDate) ORDER BY YEAR(m.releaseDate)")
    List<Object[]> countMoviesByGenreIdOrderByYear(@Param("genreId") Integer genreId);
    
    @Query("SELECT MIN(YEAR(m.releaseDate)) FROM Movie m")
    Optional<Integer> findMinYear();
    
    @Query("SELECT MAX(YEAR(m.releaseDate)) FROM Movie m")
    Optional<Integer> findMaxYear();
    
    @Query("SELECT AVG(m.voteAverage) FROM Movie m WHERE " +
    		"EXISTS (SELECT 1 FROM Movie m2 JOIN m2.genreIds g WHERE m2 = m AND g IN :genreId)")
    float getAvgRatingByGenre(@Param("genreId") Integer genreId);
}
