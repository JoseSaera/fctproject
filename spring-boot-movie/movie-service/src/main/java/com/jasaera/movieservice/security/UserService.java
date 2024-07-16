package com.jasaera.movieservice.security;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jasaera.movieservice.dto.ExtendedMovieDTO;
import com.jasaera.movieservice.service.MovieService;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MovieService movieService;

	public Set<Long> getSeeLater(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		return user.getSeeLater();
	}
	
    public List<ExtendedMovieDTO> getSeeLaterMovies(Long userId) {
        Set<Long> favorites = getSeeLater(userId);
        return movieService.getByIds(favorites);
    }

	@Transactional
	public void addMovieToSeeLater(Long userId, Long movieId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		user.getSeeLater().add(movieId);
		userRepository.save(user);
	}

	@Transactional
	public void removeMovieFromSeeLater(Long userId, Long movieId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		user.getSeeLater().remove(movieId);
		userRepository.save(user);
	}

	public Set<Long> getFavorites(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		return user.getFavorites();
	}
	
    public List<ExtendedMovieDTO> getFavoritesMovies(Long userId) {
        Set<Long> favorites = getFavorites(userId);
        return movieService.getByIds(favorites);
    }

	@Transactional
	public void addMovieToFavorites(Long userId, Long movieId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		user.getFavorites().add(movieId);
		userRepository.save(user);
	}

	@Transactional
	public void removeMovieFromFavorites(Long userId, Long movieId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		user.getFavorites().remove(movieId);
		userRepository.save(user);
	}
}
