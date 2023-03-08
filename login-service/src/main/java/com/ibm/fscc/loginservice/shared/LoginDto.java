package com.ibm.fscc.loginservice.shared;

import java.io.Serializable;

public class LoginDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String passwordHash;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	

}
