package com.ibm.fscc.employeeservice.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtRequestFilterConfigurer 
		extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	
	private JwtTokenUtil jwtTokenUtil;
	
	public JwtRequestFilterConfigurer(JwtTokenUtil jwtTokenUtil) {
		this.jwtTokenUtil = jwtTokenUtil;
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		JwtRequestFilter customFilter = new JwtRequestFilter(jwtTokenUtil);
		http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
