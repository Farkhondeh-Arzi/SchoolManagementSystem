package com.example.sm.security;

import static com.example.sm.constants.SecurityConstants.EXPIRATION_TIME;
import static com.example.sm.constants.SecurityConstants.HEADER_NAME;
import static com.example.sm.constants.SecurityConstants.KEY;
import static com.example.sm.constants.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.sm.model.User;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
          // Parameter format
		String username = req.getParameter("username");
		String password = req.getParameter("password");

		return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		User user = (User) auth.getPrincipal();

		String token = JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).withClaim("role", user.getRole())
				.sign(Algorithm.HMAC512(KEY.getBytes()));

		res.addHeader(HEADER_NAME, TOKEN_PREFIX + token);
	}
}
