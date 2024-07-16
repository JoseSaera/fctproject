package com.jasaera.movieservice.security;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jasaera.movieservice.dto.ExtendedMovieDTO;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/{userId}/favorites")
    public ResponseEntity<Set<Long>> getUserFavorites(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getFavorites(userId));
    }
    
    @GetMapping("/{userId}/favorites-movies")
    public ResponseEntity<List<ExtendedMovieDTO>> getUserFavoritesMovies(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getFavoritesMovies(userId));
    }
    
    @GetMapping("/{userId}/see-later")
    public ResponseEntity<Set<Long>> getUserSeeLater(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getSeeLater(userId));
    }
    
    @GetMapping("/{userId}/see-later-movies")
    public ResponseEntity<List<ExtendedMovieDTO>> getUserSeeLaterMovies(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getSeeLaterMovies(userId));
    }

    @PostMapping("/{userId}/see-later/{movieId}")
    public ResponseEntity<Void> addMovieToSeeLater(@PathVariable Long userId, @PathVariable Long movieId) {
        userService.addMovieToSeeLater(userId, movieId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/see-later/{movieId}")
    public ResponseEntity<Void> removeMovieFromSeeLater(@PathVariable Long userId, @PathVariable Long movieId) {
        userService.removeMovieFromSeeLater(userId, movieId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/favorites/{movieId}")
    public ResponseEntity<Void> addMovieToFavorites(@PathVariable Long userId, @PathVariable Long movieId) {
        userService.addMovieToFavorites(userId, movieId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/favorites/{movieId}")
    public ResponseEntity<Void> removeMovieFromFavorites(@PathVariable Long userId, @PathVariable Long movieId) {
        userService.removeMovieFromFavorites(userId, movieId);
        return ResponseEntity.ok().build();
    }
    
}
