package com.ibm.fscc.loginservice.services;

import org.springframework.http.ResponseEntity;

import com.ibm.fscc.loginservice.data.LoginEntity;
import com.ibm.fscc.loginservice.jwt.JwtRequest;

public interface LoginService {

	public String login(String username, String password);
	public boolean isValidToken(String token);
	public String createNewToken(String token);
	public String getPasswordHashByEmail(String email);
	
}
