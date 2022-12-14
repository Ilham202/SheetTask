package com.task.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.task.service.JwtUserDetailsService;
import com.task.service.ReadService;
import com.task.config.JwtTokenUtil;
import com.task.model.JwtRequest;
import com.task.model.JwtResponse;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	ReadService readService;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	
	@PostMapping("/authenticate")
	public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws DisabledException, BadCredentialsException {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity
				.ok( new JwtResponse(token,userDetailsService.getUserId(authenticationRequest.getUsername())));
	}
	

	private void authenticate(String username, String password) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException ex) {
			throw new DisabledException("USER_DISABLED", ex);
		} catch (BadCredentialsException ex) {
			throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
		}
	}

}