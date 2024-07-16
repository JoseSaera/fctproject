package com.jasaera.movieservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.jasaera.movieservice.dto.ExtendedMovieDTO;
import com.jasaera.movieservice.dto.GenreDTO;
import com.jasaera.movieservice.dto.MovieCountByYear;
import com.jasaera.movieservice.dto.MovieDTO;
import com.jasaera.movieservice.entity.Movie;
import com.jasaera.movieservice.mapper.MovieMapper;
import com.jasaera.movieservice.repository.MovieRepository;

@Service
public class MovieService {

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private GenreService genreService;

	public ExtendedMovieDTO createExtendedMovieDTO(MovieDTO movieDTO) {
		List<GenreDTO> genres = genreService.getAll();
		return MovieMapper.toExtendedDTO(movieDTO, genres);
	}

	public Page<ExtendedMovieDTO> getAll(int skip, int limit) {
		Page<Movie> pageMovie = movieRepository.findAll(PageRequest.of(skip, limit));
		Page<ExtendedMovieDTO> pageExtendedMovieDTO = pageMovie.map(movie -> {
			MovieDTO movieDTO = MovieMapper.toDTO(movie);
			return createExtendedMovieDTO(movieDTO);
		});

		return pageExtendedMovieDTO;
	}

	public List<ExtendedMovieDTO> getByIds(Set<Long> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }
        List<Movie> movies = movieRepository.findByIdIn(ids);
        return movies.stream()
                     .map(movie -> {
                         MovieDTO movieDTO = MovieMapper.toDTO(movie);
                         return createExtendedMovieDTO(movieDTO);
                     })
                     .collect(Collectors.toList());
    }

	public Page<ExtendedMovieDTO> getByTitle(int skip, int limit, String title) {
		if (title.isEmpty()) {
			return Page.empty();
		}
		Pageable pageable = PageRequest.of(skip, limit);
		Page<Movie> pageMovie = movieRepository.findByTitleCustom(title, pageable);
		Page<ExtendedMovieDTO> pageExtendedMovieDTO = pageMovie.map(movie -> {
			MovieDTO movieDTO = MovieMapper.toDTO(movie);
			return createExtendedMovieDTO(movieDTO);
		});

		return pageExtendedMovieDTO;
	}

	public Page<ExtendedMovieDTO> getFilteredMovies(int skip, int limit, String title, List<Long> genreIds,
			Integer minYear, Integer maxYear) {
		if (title.isEmpty() || genreIds.isEmpty()) {
			return Page.empty();
		}
		Pageable pageable = PageRequest.of(skip, limit);
		Page<Movie> pageMovie = movieRepository.findWithFilters(title, genreIds, minYear, maxYear, pageable);

		Page<ExtendedMovieDTO> pageExtendedMovieDTO = pageMovie.map(movie -> {
			MovieDTO movieDTO = MovieMapper.toDTO(movie);
			return createExtendedMovieDTO(movieDTO);
		});

		return pageExtendedMovieDTO;
	}

	public Page<ExtendedMovieDTO> getTopPopularity(int skip, int limit) {
		Pageable pageable = PageRequest.of(skip, limit);
		Page<Movie> pageMovie = movieRepository.findTopMoviesOrderedByPopularityDesc(pageable);
		Page<ExtendedMovieDTO> pageExtendedMovieDTO = pageMovie.map(movie -> {
			MovieDTO movieDTO = MovieMapper.toDTO(movie);
			return createExtendedMovieDTO(movieDTO);
		});

		return pageExtendedMovieDTO;
	}

	public Page<ExtendedMovieDTO> getTopRating(int skip, int limit, Long minVoteCount) {
		Pageable pageable = PageRequest.of(skip, limit);
		Page<Movie> pageMovie = movieRepository.findTopMoviesOrderedByVoteAverageDesc(pageable, minVoteCount);
		Page<ExtendedMovieDTO> pageExtendedMovieDTO = pageMovie.map(movie -> {
			MovieDTO movieDTO = MovieMapper.toDTO(movie);
			return createExtendedMovieDTO(movieDTO);
		});

		return pageExtendedMovieDTO;
	}

	public ArrayList<Integer> countByRatingAndGenre(Integer genreId) {
		ArrayList<Integer> ratings = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			ratings.add(movieRepository.countMoviesByGenreIdAndVoteAverage(genreId, i, i + 1));
		}
		return ratings;
	}

	public List<MovieCountByYear> countByGenreOrderByYear(Integer genreId) {
		List<Object[]> results = movieRepository.countMoviesByGenreIdOrderByYear(genreId);
		List<MovieCountByYear> movieCounts = new ArrayList<>();

		for (Object[] result : results) {
			int releaseYear = (int) result[0];
			long movieCount = (Long) result[1];
			movieCounts.add(new MovieCountByYear(releaseYear, movieCount));
		}
		return movieCounts;
	}

	public Map<String, Integer> getMinMaxYear() {
		Map<String, Integer> result = new HashMap<>();

		Optional<Integer> minYear = movieRepository.findMinYear();
		minYear.ifPresent(year -> result.put("minYear", year));
		Optional<Integer> maxYear = movieRepository.findMaxYear();
		maxYear.ifPresent(year -> result.put("maxYear", year));

		return result;
	}

	public float getRatingAverageByGenreId(Integer genreId) {
		return movieRepository.getAvgRatingByGenre(genreId);
	}

}
