package com.example.sm.security;

import static com.example.sm.constants.SecurityConstants.HEADER_NAME;
import static com.example.sm.constants.SecurityConstants.KEY;
import static com.example.sm.constants.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static java.util.Arrays.stream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class AuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String header = request.getHeader(HEADER_NAME);

		if (header == null || !header.startsWith(TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}

		UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);

		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_NAME);
		if (token != null) {

			// parse the token.
			String username = JWT.require(Algorithm.HMAC512(KEY.getBytes())).build()
					.verify(token.replace(TOKEN_PREFIX, "")).getSubject();

			List<SimpleGrantedAuthority> roles = new ArrayList<>();
			
			String claim = JWT.require(Algorithm.HMAC512(KEY.getBytes())).build()
					.verify(token.replace(TOKEN_PREFIX, "")).getClaim("role").asString();
			roles.add(new SimpleGrantedAuthority(claim));
            
			if (username != null) {

				// new arraylist means authorities
				return new UsernamePasswordAuthenticationToken(username, null, roles);
			}
			return null;
		}
		return null;
	}
}
