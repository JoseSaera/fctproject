package com.jasaera.movieservice.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.jasaera.movieservice.dto.ExtendedMovieDTO;
import com.jasaera.movieservice.dto.GenreDTO;
import com.jasaera.movieservice.dto.MovieDTO;
import com.jasaera.movieservice.entity.Movie;

public class MovieMapper {
	
	private static final ModelMapper modelMapper = new ModelMapper();
	
	private static String posterUrl = "https://image.tmdb.org/t/p/w500";
	
	public static Movie toEntity(MovieDTO movieDTO) {
		Movie movie = new Movie();
		movie.setId(movieDTO.getId());
		movie.setOriginalLanguage(movieDTO.getOriginalLanguage());
		movie.setVoteAverage(movieDTO.getVoteAverage());
		movie.setOriginalTitle(movieDTO.getOriginalTitle());
		movie.setBackdropPath(movieDTO.getBackdropPath());
		movie.setPopularity(movieDTO.getPopularity());
		movie.setPosterPath(movieDTO.getPosterPath());
		movie.setTitle(movieDTO.getTitle());
		movie.setOverview(movieDTO.getOverview());
		movie.setAdult(movieDTO.isAdult());
		movie.setReleaseDate(movieDTO.getReleaseDate());
		movie.setVideo(movieDTO.isVideo());
		movie.setVoteCount(movieDTO.getVoteCount());
		movie.setGenreIds(movieDTO.getGenreIds());
		return movie;
	}

    public static MovieDTO toDTO(Movie movie) {
        MovieDTO movieDTO = modelMapper.map(movie, MovieDTO.class);
        movieDTO.setPosterUrl(posterUrl+movie.getPosterPath());
        return movieDTO;
    }

    public static ExtendedMovieDTO toExtendedDTO(MovieDTO movieDTO, List<GenreDTO> genresDTO) {
        ExtendedMovieDTO extendedMovieDTO = modelMapper.map(movieDTO, ExtendedMovieDTO.class);
        List<GenreDTO> selectedGenres = new ArrayList<>();
        for (Long genreId : movieDTO.getGenreIds()) {
            for (GenreDTO genreDTO : genresDTO) {
                if (genreDTO.getId().equals(genreId)) {
                    selectedGenres.add(genreDTO);
                    break;
                }
            }
        }
        extendedMovieDTO.setGenresDTO(selectedGenres);
        return extendedMovieDTO;
    }

}
