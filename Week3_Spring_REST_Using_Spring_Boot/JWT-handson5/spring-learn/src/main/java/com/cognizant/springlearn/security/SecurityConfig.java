package com.cognizant.springlearn.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security configuration.
 *
 * Progression of this exercise:
 *
 * Step 1 – All URLs secured, single auto-generated password
 *   (just adding @EnableWebSecurity was enough)
 *
 * Step 2 – Two in-memory users (admin/user) with BCrypt-encrypted passwords
 *   and role-based antMatcher rules
 *
 * Step 3 – JWT filter added; /authenticate uses Basic auth to get a token,
 *   all other requests must carry the JWT Bearer token
 *
 * In-memory users:
 *   username: admin  password: pwd  role: ADMIN
 *   username: user   password: pwd  role: USER
 *
 * curl examples:
 *   # Get JWT token (Basic auth)
 *   curl -s -u user:pwd http://localhost:8090/authenticate
 *
 *   # Use JWT token for protected endpoint
 *   curl -s -H "Authorization: Bearer <token>" http://localhost:8090/countries
 *
 *   # Wrong credentials → 401
 *   curl -s -u user:wrong http://localhost:8090/authenticate
 *
 *   # Wrong role (admin cannot access /countries in step 2) → 403
 *   curl -s -u admin:pwd http://localhost:8090/countries
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    // ------------------------------------------------------------------
    // Password encoder bean – BCrypt is the recommended choice
    // ------------------------------------------------------------------

    @Bean
    public PasswordEncoder passwordEncoder() {
        LOGGER.info("Start");
        return new BCryptPasswordEncoder();
    }

    // ------------------------------------------------------------------
    // In-memory user store
    // ------------------------------------------------------------------

    /**
     * Configures two in-memory users:
     *   admin / pwd → ADMIN role
     *   user  / pwd → USER  role
     *
     * NOTE: In production, credentials would be loaded from a database
     * (covered in the Spring Data JPA module).
     */
    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {

        AuthenticationManagerBuilder builder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        builder.inMemoryAuthentication()
                .withUser("admin")
                    .password(passwordEncoder.encode("pwd"))
                    .roles("ADMIN")
                .and()
                .withUser("user")
                    .password(passwordEncoder.encode("pwd"))
                    .roles("USER");

        return builder.build();
    }

    // ------------------------------------------------------------------
    // HTTP security – URL authorisation + JWT filter
    // ------------------------------------------------------------------

    /**
     * Security filter chain:
     *
     * - CSRF disabled (stateless REST API does not need it)
     * - HTTP Basic authentication enabled (for /authenticate endpoint)
     * - /authenticate accessible to USER and ADMIN roles
     * - All other requests require authentication (JWT Bearer token)
     * - JwtAuthorizationFilter intercepts every request to validate JWT
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity,
            AuthenticationManager authenticationManager) throws Exception {

        httpSecurity
            .csrf(csrf -> csrf.disable())
            .httpBasic(httpBasic -> {})          // enables Basic auth for /authenticate
            .authorizeHttpRequests(auth -> auth
                // /authenticate – accessible to both roles (Basic auth)
                .requestMatchers("/authenticate").hasAnyRole("USER", "ADMIN")
                // All other requests must carry a valid JWT
                .anyRequest().authenticated()
            )
            // Register the JWT filter that validates Bearer tokens
            .addFilter(new JwtAuthorizationFilter(authenticationManager));

        return httpSecurity.build();
    }
}
