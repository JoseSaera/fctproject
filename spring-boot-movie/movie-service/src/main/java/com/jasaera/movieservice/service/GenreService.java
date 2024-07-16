package com.jasaera.movieservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jasaera.movieservice.dto.GenreDTO;
import com.jasaera.movieservice.entity.Genre;
import com.jasaera.movieservice.mapper.GenreMapper;
import com.jasaera.movieservice.repository.GenreRepository;

@Service
public class GenreService {
	
	@Autowired
	GenreRepository genreRepository;
	
	public List<GenreDTO> getAll() {
	    List<Genre> genreList = genreRepository.findAll();
	    
	    List<GenreDTO> genreListDTO = genreList.stream()
	                                           .map(genre -> GenreMapper.toDTO(genre))
	                                           .collect(Collectors.toList());
	    
	    return genreListDTO;
	}
	
	public List<GenreDTO> getGenresByIds(List<Long> genreIds) {
	    List<GenreDTO> genresDTO = new ArrayList<>();
	    
	    for (Long genreId : genreIds) {
	        GenreDTO genreDTO = getGenreById(genreId);
	        if (genreDTO != null) {
	            genresDTO.add(genreDTO);
	        }
	    }
	    
	    return genresDTO;
	}
	
	public GenreDTO getGenreById(Long genreId) {
	    Genre genre = genreRepository.findById(genreId).orElse(null);
	    if (genre != null) {
	        GenreDTO genreDTO = new GenreDTO();
	        genreDTO.setId(genre.getId());
	        genreDTO.setName(genre.getName());
	        return genreDTO;
	    } else {
	        return null; 
	    }
	}
	
	public List<String> getGenreNamesByIds(List<Long> genreIds) {		
		List<Genre> genres = genreRepository.findAllById(genreIds);
		
		List<String> genreNames = genres.stream()
										.map(genre -> genre.getName())
										.collect(Collectors.toList());
		return genreNames;
	}
	
	public List<Long> getGenreIdsByNames(List<String> genreNames) {
		List<Genre> genres = genreRepository.findAllByNameIn(genreNames);
		
		List<Long> genreIds = genres.stream()
										.map(genre -> genre.getId())
										.collect(Collectors.toList());
		return genreIds;
	}
}
