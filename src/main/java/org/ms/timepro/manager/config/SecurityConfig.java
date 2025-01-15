package org.ms.timepro.manager.config;

import java.util.Arrays;
import java.util.List;

import org.ms.timepro.manager.jwt.JwtAuthenticationFilter;
import org.ms.timepro.manager.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService userDetailsService;
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	
    	List<AntPathRequestMatcher> antPathMatchers = Arrays.asList(
    		    new AntPathRequestMatcher("/"),
    		    new AntPathRequestMatcher("/favicon.ico"),
    		    new AntPathRequestMatcher("/**/*.png"),
    		    new AntPathRequestMatcher("/**/*.gif"),
    		    new AntPathRequestMatcher("/**/*.svg"),
    		    new AntPathRequestMatcher("/**/*.jpg"),
    		    new AntPathRequestMatcher("/**/*.html"),
    		    new AntPathRequestMatcher("/**/*.css"),
    		    new AntPathRequestMatcher("/**/*.js"),
    		    new AntPathRequestMatcher("/**/v2/api-docs"),
    		    new AntPathRequestMatcher("/**/v3/api-docs"),
    		    new AntPathRequestMatcher("/configuration/ui"),
    		    new AntPathRequestMatcher("/swagger-resources/**"),
    		    new AntPathRequestMatcher("/login/**"),
    		    new AntPathRequestMatcher("/configuration/security"),
    		    new AntPathRequestMatcher("/**/swagger-ui/**"),
    		    new AntPathRequestMatcher("/swagger-ui.html"),
    		    new AntPathRequestMatcher("/**/swagger-ui/index.html"),
    		    new AntPathRequestMatcher("/api/pdr/v01/cross/security/swagger-ui/index.html"),
    		    new AntPathRequestMatcher("/webjars/**")
    		);

        http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(request -> {
            for (AntPathRequestMatcher antPathMatcher : antPathMatchers) {
                request.requestMatchers(antPathMatcher).permitAll();
            }
            request.anyRequest().authenticated();
        }).sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}