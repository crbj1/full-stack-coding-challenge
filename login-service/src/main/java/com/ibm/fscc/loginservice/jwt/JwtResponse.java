package com.ibm.fscc.loginservice.jwt;

import java.io.Serializable;
import java.util.Date;

public class JwtResponse {
	
	private String jwtToken;
	
	public JwtResponse(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	
	public JwtResponse() {
		
	}
	
	public String getJwtToken() {
		return this.jwtToken;
	}
	
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

}
