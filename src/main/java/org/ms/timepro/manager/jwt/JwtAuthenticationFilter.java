package org.ms.timepro.manager.jwt;

import java.io.IOException;
import java.security.SignatureException;
import java.util.Objects;

import org.ms.timepro.manager.exception.TokenValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JwtAuthenticationFilter - Spring Boot config class
 *
 * @author Jesus garcia
 * @since 0.0.1
 * @version jdk-21
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final String BEARER_PREFIX = "Bearer ";

	private final JwtTokenProvider tokenProvider;

	/**
	 * Processes the incoming request to validate the JWT token and set up the
	 * security context.
	 *
	 * @param request     The HTTP request to process.
	 * @param response    The HTTP response.
	 * @param filterChain The filter chain to continue processing.
	 * @throws ServletException If an exception occurs while processing the request.
	 * @throws IOException      If an I/O exception occurs while processing the
	 *                          request.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt = getJwtFromRequest(request);
		if (Objects.nonNull(jwt)) {
			try {
				if (tokenProvider.validateToken(jwt)) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							tokenProvider.getUserPrincipalFromToken(jwt), null, tokenProvider.getAuthorityList(jwt));

					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			} catch (IOException | SignatureException ex) {
				log.error("Token validation failed: " + ex.getMessage());
				throw new TokenValidationException("Error validating JWT token", HttpStatus.UNAUTHORIZED.value(), ex);
			}
		}
		filterChain.doFilter(request, response);
	}

	/**
	 * Extracts the JWT token from the request's "Authorization" header.
	 *
	 * @param request The HTTP request to extract the token from.
	 * @return The extracted JWT token, or null if not found.
	 */
	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
			return bearerToken.substring(BEARER_PREFIX.length());
		}
		return null;
	}
}
