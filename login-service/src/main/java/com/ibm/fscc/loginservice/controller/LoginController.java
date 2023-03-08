package com.ibm.fscc.loginservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.fscc.loginservice.data.LoginEntity;
import com.ibm.fscc.loginservice.jwt.JwtRequest;
import com.ibm.fscc.loginservice.jwt.JwtResponse;
import com.ibm.fscc.loginservice.services.LoginService;

@Controller
@RequestMapping("/api")
public class LoginController {

	//@Autowired
	//private Environment env;
	
	@Autowired
	private LoginService service;
	
	/*
	@PostMapping(path="/addUser")
	public LoginEntity addUser(@RequestBody LoginEntity login) {
		return service.addUser(login);
	} */
	
	@CrossOrigin("*")
	@PostMapping("/signin")
	@ResponseBody
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) {
		String token = service.login(authRequest.getUsername(), authRequest.getPassword());
		HttpHeaders headers = new HttpHeaders();
		List<String> headerList = new ArrayList<>();
		List<String> exposeList = new ArrayList<>();
		headerList.add("Content-Type");
		headerList.add("Accept");
		headerList.add("X-Requested-With");
		headerList.add("Authorization");
		headers.setAccessControlAllowHeaders(headerList);
		exposeList.add("Authorization");
		headers.setAccessControlExposeHeaders(exposeList);
		headers.set("Authorization", token);
		return new ResponseEntity<JwtResponse>(new JwtResponse(token), headers, HttpStatus.CREATED);
	}
	
	//If request reaches here, it means it is a valid token.
	@PostMapping("/valid/token")
	@ResponseBody
	public boolean isValidToken(@RequestHeader(value="Authorization") String token) {
		return true;
	}
	
	
	@PostMapping("/getPasswordHash")
	@ResponseBody
	public String getPasswordHashByEmail(@RequestBody String email) {
		return service.getPasswordHashByEmail(email);
	}

	/*
	@GetMapping(path="/status/check")
	public String status() {
		return "Working on port " + env.getProperty("server.port");
	} */
	
	

}
