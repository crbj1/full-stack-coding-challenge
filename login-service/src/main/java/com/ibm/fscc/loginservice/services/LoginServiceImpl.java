package com.ibm.fscc.loginservice.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.ibm.fscc.loginservice.data.LoginEntity;
import com.ibm.fscc.loginservice.exceptions.UnauthorizedException;
import com.ibm.fscc.loginservice.jwt.JwtRequest;
import com.ibm.fscc.loginservice.jwt.JwtResponse;
import com.ibm.fscc.loginservice.jwt.JwtTokenUtil;
import com.ibm.fscc.loginservice.repository.LoginRepository;

@Service
public class LoginServiceImpl implements UserDetailsService, LoginService {
	
	@Autowired
	private LoginRepository repository;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			LoginEntity entity = repository.findById(username).get();
			return new User(username, entity.getPasswordHash(), new ArrayList<>());
		} catch (NoSuchElementException e) {
			throw new UsernameNotFoundException("User not found with email: " + username);
		}
	}
	
	@Override
	public String login(String username, String password) {
		authenticate(username, password);
		
		final UserDetails userDetails = loadUserByUsername(username);
		return jwtTokenUtil.generateToken(new HashMap<>(), userDetails);
	}
	
	private void authenticate(String username, String password) throws UnauthorizedException {
		try {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new UnauthorizedException("User disabled");
		} catch (LockedException e) {
			throw new UnauthorizedException("Account is locked");
		} catch (BadCredentialsException e) {
			throw new UnauthorizedException("Invalid credentials");
		}
	}
	
	/*
	@Override
	public LoginEntity addUser(LoginEntity login) {
		if (repository.findById(login.getEmail()).isPresent()) {
			throw new UnauthorizedException("A user with this email already exists");
		}
		// Received login entity has unhashed password
		String hash = BCrypt.hashpw(login.getPasswordHash(), BCrypt.gensalt());
		login.setPasswordHash(hash);
		return repository.save(login);
	} */
	
	//@Override
	public LoginEntity findByEmail(String email) {
		try {
			return repository.findById(email).get();
		} catch (NoSuchElementException e) {
			throw new UnauthorizedException("There is no user with that email");
		}
	}
	
	@Override
	public String getPasswordHashByEmail(String email) {
		return findByEmail(email).getPasswordHash();
	}
	
	@Override
	public boolean isValidToken(String token) {
		return jwtTokenUtil.validateToken(token); 
	}
	
	@Override
	public String createNewToken(String token) {
		String username = jwtTokenUtil.getUsernameFromToken(token);
		final UserDetails userDetails = loadUserByUsername(username);
		return jwtTokenUtil.generateToken(new HashMap<>(), userDetails);
	}
	
}
