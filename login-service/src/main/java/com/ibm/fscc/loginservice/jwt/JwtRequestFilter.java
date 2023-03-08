package com.ibm.fscc.loginservice.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ibm.fscc.loginservice.exceptions.UnauthorizedException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

public class JwtRequestFilter extends GenericFilterBean {
	
	private JwtTokenUtil jwtTokenUtil;
	
	public JwtRequestFilter(JwtTokenUtil jwtTokenUtil) {
		this.jwtTokenUtil = jwtTokenUtil;
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, 
			FilterChain chain) throws ServletException, IOException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		String token = jwtTokenUtil.resolveToken(request);
		
		if (token != null) {
			try {
				jwtTokenUtil.validateToken(token);
			} catch (JwtException | IllegalArgumentException e) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
				throw new UnauthorizedException("Invalid JWT token");
			}
			
			Authentication auth = token != null ? jwtTokenUtil.getAuthentication(token) : null;
			
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		
		chain.doFilter(req, res);
	}

}
