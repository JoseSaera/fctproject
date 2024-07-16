package com.jasaera.tests;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jasaera.movieservice.MovieServiceApplication;
import com.jasaera.movieservice.dto.GenreDTO;
import com.jasaera.movieservice.entity.Genre;
import com.jasaera.movieservice.repository.GenreRepository;
import com.jasaera.movieservice.service.GenreService;

import jakarta.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MovieServiceApplication.class)
@Transactional
@ActiveProfiles("test")
public class GenreServiceTest {
	
	@Autowired
	private GenreRepository genreRepository;
	
	@Autowired
	private GenreService genreService;
	
	@BeforeEach
	public void setUp() {
		Genre genre1 = new Genre();
		genre1.setId(1L);
		genre1.setName("Genre 1");
		
		Genre genre2 = new Genre();
		genre2.setId(2L);
		genre2.setName("Genre 2");
		
		Genre genre3 = new Genre();
		genre3.setId(3L);
		genre3.setName("Genre 3");
		
		genreRepository.save(genre1);
		genreRepository.save(genre2);
		genreRepository.save(genre3);
	}

	@Test
	public void testGetAll() {
		List<GenreDTO> genres = genreService.getAll();
		assert genres.size() == 3;
		assert genres.get(0).getId() == 1;
		assert genres.get(0).getName() == "Genre 1";
	}
	
	@Test
	public void testGetGenresByIds() {
		assert genreService.getGenreNamesByIds(List.of(1L, 2L, 3L)).size() == 3;
		assert genreService.getGenreNamesByIds(List.of(1L)).size() == 1;
	}
	
	@Test
	public void testGetGenreById() {
		GenreDTO genre = genreService.getGenreById(1L);
		assert genre.getName() == "Genre 1";
	}
	
	@Test
	public void testGetGenreNamesByIds() {
		List<String> genre = genreService.getGenreNamesByIds(List.of(1L, 2L));
		assert genre.size() == 2;
		assert genre.contains("Genre 1");
		assert genre.contains("Genre 2");
	}
	
	@Test
	public void testGetGenreIdsByNames() {
		List<Long> genre = genreService.getGenreIdsByNames(List.of("Genre 2", "Genre 3"));
		assert genre.size() == 2;
		assert genre.contains(2L);
		assert genre.contains(3L);
	}
}
