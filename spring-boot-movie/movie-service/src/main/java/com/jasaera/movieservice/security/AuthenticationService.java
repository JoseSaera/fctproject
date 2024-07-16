package com.jasaera.movieservice.security;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
	
	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final JwtService jwtService;
	
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
			AuthenticationManager authenticationManager) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}

	public AuthenticationResponse register(User request) {
		User user = new User();
		user.setName(request.getName());
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		
		userRepository.save(user);
		
		Long id = user.getId();
		String token = jwtService.generateToken(user);
	    Set<Long> seeLater = Collections.emptySet();
	    Set<Long> favorites = Collections.emptySet();

	    return new AuthenticationResponse(id, token, seeLater, favorites);
	}
	
	public AuthenticationResponse authenticate(User request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getUsername(), 
						request.getPassword()
				)
		);
		
		User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
		Long id = user.getId();
		String token = jwtService.generateToken(user);
	    Set<Long> seeLater = user.getSeeLater();
	    Set<Long> favorites = user.getFavorites();

	    return new AuthenticationResponse(id, token, seeLater, favorites);	
	}

}
