package com.ibm.fscc.employeeservice.jwt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ibm.fscc.employeeservice.exceptions.ResourceNotFoundException;
import com.ibm.fscc.employeeservice.services.DiscoveryService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//Adapted from code from javainuse
@Component
public class JwtTokenUtil {
	
	//@Value("${jwt.secret}")
	//private String secret;
	
	@Autowired
	private DiscoveryService discoveryService;
	
	private final RestTemplate restTemplate;
	
	public JwtTokenUtil(RestTemplateBuilder builder) {
		this.restTemplate = builder.build();
	}
	
	/*
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	private boolean isExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	/*
	public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	} 
	
	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isExpired(token));
	} */
	
	public boolean validateToken(String bearerToken) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", bearerToken);
		HttpEntity<Map<String, Object>> entity = new HttpEntity<Map<String, Object>>(headers);
		
		String validateUrl = discoveryService.getValidateUrl().toString();
		
		if (validateUrl == null) {
			throw new ResourceNotFoundException("The URL for the Login Service was not found");
		}
		
		return restTemplate.exchange(validateUrl, HttpMethod.POST, entity, boolean.class).getBody();
	}
	
	public Authentication getAuthentication(String token) {
		return new UsernamePasswordAuthenticationToken("", "", new ArrayList<>());
	}

}
