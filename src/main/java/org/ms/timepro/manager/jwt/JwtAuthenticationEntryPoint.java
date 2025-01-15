package org.ms.timepro.manager.jwt;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * JwtAuthenticationEntryPoint - Spring Boot config class
 *
 * @author Jesus garcia
 * @since 0.0.1
 * @version jdk-21
 */
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Invoked when an authentication exception occurs, responding with an unauthorized error message in JSON format.
     *
     * @param request       The HTTP request that resulted in the authentication exception.
     * @param response      The HTTP response to populate with error details.
     * @param authException The authentication exception that occurred.
     * @throws IOException      If an I/O exception occurs while writing the error response.
     * @throws ServletException If a servlet-related exception occurs.
     */
	@Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
    	log.error("Responding with unauthorized error. Message - {}", e.getMessage());
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                "Sorry, You're not authorized to access this resource.");
    }
    
}
