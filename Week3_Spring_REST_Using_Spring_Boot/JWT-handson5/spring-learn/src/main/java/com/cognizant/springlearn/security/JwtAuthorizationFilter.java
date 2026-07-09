package com.cognizant.springlearn.security;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Spring Security filter that validates the JWT Bearer token on every incoming
 * request (except /authenticate, which uses Basic auth).
 *
 * JWT Process Flow:
 *   1. Client obtains JWT from /authenticate with Basic credentials
 *   2. Client attaches JWT in "Authorization: Bearer <token>" header
 *   3. This filter intercepts every request, extracts the token,
 *      validates the signature, and sets the authenticated user in the
 *      Spring Security context so downstream controllers are accessible.
 *
 * Extends BasicAuthenticationFilter so it participates in the standard
 * Spring Security filter chain.
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    /** Must match the key used when signing the token in AuthenticationController. */
    private static final String SECRET_KEY = "secretkey";

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        LOGGER.info("Start");
        LOGGER.debug("AuthenticationManager : {}", authenticationManager);
    }

    // ------------------------------------------------------------------
    // Filter logic – called for every request
    // ------------------------------------------------------------------

    /**
     * Checks for a "Bearer " Authorization header.
     * If present, validates the JWT and sets the Spring Security context.
     * If absent (or not Bearer), passes the request straight through –
     * Spring Security's Basic auth filter will handle it instead.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain)
            throws IOException, ServletException {

        LOGGER.info("Start");

        String header = req.getHeader("Authorization");
        LOGGER.debug("Authorization header : {}", header);

        // If no Bearer token, let the filter chain continue
        // (Basic auth will handle /authenticate)
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(req, res);
            return;
        }

        // Validate the JWT and obtain an Authentication object
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        // Set authentication in the Security context so the request is authorised
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);

        LOGGER.info("End");
    }

    // ------------------------------------------------------------------
    // JWT validation
    // ------------------------------------------------------------------

    /**
     * Parses and validates the JWT from the Authorization header.
     *
     * @param request the incoming HTTP request
     * @return an authenticated token if the JWT is valid; null otherwise
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token != null) {
            try {
                // Parse and verify the JWT signature
                Jws<Claims> jws = Jwts.parser()
                        .setSigningKey(SECRET_KEY)
                        .parseClaimsJws(token.replace("Bearer ", ""));

                // Extract the subject (username) from the token body
                String user = jws.getBody().getSubject();
                LOGGER.debug("JWT subject (user) : {}", user);

                if (user != null) {
                    // Return an authenticated token with empty authorities list
                    // (roles were validated when the JWT was issued)
                    return new UsernamePasswordAuthenticationToken(
                            user, null, new ArrayList<>());
                }

            } catch (JwtException ex) {
                // Invalid signature, expired token, etc.
                LOGGER.warn("JWT validation failed : {}", ex.getMessage());
                return null;
            }
            return null;
        }
        return null;
    }
}
