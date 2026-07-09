package com.cognizant.springlearn.controller;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * REST Controller – Authentication / JWT generation.
 *
 * JWT Process:
 *   Step 1 – Client sends Basic credentials to /authenticate
 *   Step 2 – Server validates credentials (handled by Spring Security), decodes
 *             the Authorization header, generates a JWT and returns it
 *   Step 3 – Client uses the returned JWT as a Bearer token in subsequent requests
 *
 * Test:
 *   curl -s -u user:pwd http://localhost:8090/authenticate
 *
 * Expected response:
 *   {"token":"eyJhbGci..."}
 *
 * Use the token for protected endpoints:
 *   curl -s -H "Authorization: Bearer <token>" http://localhost:8090/countries
 */
@RestController
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    /** Secret key used to sign and verify JWTs. In production, store this securely. */
    private static final String SECRET_KEY = "secretkey";

    /** Token validity: 20 minutes (1 200 000 ms). */
    private static final long TOKEN_VALIDITY_MS = 1_200_000L;

    // ------------------------------------------------------------------
    // GET /authenticate
    // ------------------------------------------------------------------

    /**
     * Accepts Basic auth credentials via the Authorization header,
     * extracts the username, generates a signed JWT and returns it.
     *
     * Spring Security has already validated the credentials before this
     * method is reached, because /authenticate requires ROLE_USER or ROLE_ADMIN.
     *
     * @param authHeader value of the "Authorization" HTTP request header
     *                   e.g. "Basic dXNlcjpwd2Q="
     * @return map containing the generated JWT under the key "token"
     */
    @GetMapping("/authenticate")
    public Map<String, String> authenticate(
            @RequestHeader("Authorization") String authHeader) {

        LOGGER.info("START");
        LOGGER.debug("Authorization header : {}", authHeader);

        String user = getUser(authHeader);
        LOGGER.debug("Authenticated user : {}", user);

        String token = generateJwt(user);
        LOGGER.debug("Generated token : {}", token);

        Map<String, String> map = new HashMap<>();
        map.put("token", token);

        LOGGER.info("END");
        return map;
    }

    // ------------------------------------------------------------------
    // Private helpers
    // ------------------------------------------------------------------

    /**
     * Decodes the Base64-encoded credentials in the Authorization header
     * and returns just the username portion.
     *
     * Authorization header format: "Basic <Base64(username:password)>"
     *
     * @param authHeader raw Authorization header value
     * @return decoded username
     */
    private String getUser(String authHeader) {
        LOGGER.info("START");

        // Strip "Basic " prefix to get the Base64-encoded credentials
        String encodedCredentials = authHeader.substring("Basic ".length());
        LOGGER.debug("Encoded credentials : {}", encodedCredentials);

        // Decode from Base64 → "username:password"
        byte[] decodedBytes = Base64.getDecoder().decode(encodedCredentials);
        String decodedCredentials = new String(decodedBytes);
        LOGGER.debug("Decoded credentials : {}", decodedCredentials);

        // Extract everything before the first colon
        String user = decodedCredentials.substring(0, decodedCredentials.indexOf(':'));
        LOGGER.debug("User : {}", user);

        LOGGER.info("END");
        return user;
    }

    /**
     * Creates and signs a JWT for the given username.
     *
     * Token claims:
     *   sub  – username (subject)
     *   iat  – issued-at (current time)
     *   exp  – expiry   (current time + TOKEN_VALIDITY_MS)
     *
     * Signed with HMAC-SHA256 using SECRET_KEY.
     *
     * @param user username to embed as the JWT subject
     * @return compact, URL-safe JWT string
     */
    private String generateJwt(String user) {
        LOGGER.info("START");
        LOGGER.debug("Generating JWT for user : {}", user);

        JwtBuilder builder = Jwts.builder();
        builder.setSubject(user);

        // Issued-at = now
        builder.setIssuedAt(new Date());

        // Expiry = now + 20 minutes
        builder.setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY_MS));

        // Sign with HS256
        builder.signWith(SignatureAlgorithm.HS256, SECRET_KEY);

        String token = builder.compact();
        LOGGER.debug("JWT generated successfully");
        LOGGER.info("END");
        return token;
    }
}
