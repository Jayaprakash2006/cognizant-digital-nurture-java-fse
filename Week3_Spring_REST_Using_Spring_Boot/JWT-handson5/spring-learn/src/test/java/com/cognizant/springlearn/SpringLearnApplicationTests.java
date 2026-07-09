package com.cognizant.springlearn;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.cognizant.springlearn.controller.AuthenticationController;
import com.cognizant.springlearn.controller.CountryController;

/**
 * Integration tests for JWT Security (Hands On 5).
 *
 * Tests:
 *  1. Context loads – controllers wired correctly
 *  2. /authenticate without credentials → 401
 *  3. /authenticate with valid Basic credentials → 200 + token in response
 *  4. /countries without token → 401
 *  5. /countries with valid JWT Bearer token → 200 + country array
 *  6. /countries with invalid/tampered JWT → 403 or 401
 */
@SpringBootTest
@AutoConfigureMockMvc
class SpringLearnApplicationTests {

    @Autowired
    private CountryController countryController;

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private MockMvc mvc;

    // -----------------------------------------------------------------------
    // 1. Context loads
    // -----------------------------------------------------------------------
    @Test
    public void contextLoads() {
        assertNotNull(countryController);
        assertNotNull(authenticationController);
    }

    // -----------------------------------------------------------------------
    // 2. /authenticate without credentials → 401
    // -----------------------------------------------------------------------
    @Test
    public void testAuthenticateWithoutCredentials() throws Exception {
        mvc.perform(get("/authenticate"))
           .andExpect(status().isUnauthorized());
    }

    // -----------------------------------------------------------------------
    // 3. /authenticate with valid user credentials → token in body
    // -----------------------------------------------------------------------
    @Test
    public void testAuthenticateReturnsToken() throws Exception {
        MvcResult result = mvc.perform(
                get("/authenticate").with(httpBasic("user", "pwd")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").exists())
            .andReturn();

        String body = result.getResponse().getContentAsString();
        assertTrue(body.contains("token"), "Response should contain a token field");
    }

    // -----------------------------------------------------------------------
    // 4. /countries without token → 401
    // -----------------------------------------------------------------------
    @Test
    public void testGetCountriesWithoutToken() throws Exception {
        mvc.perform(get("/countries"))
           .andExpect(status().isUnauthorized());
    }

    // -----------------------------------------------------------------------
    // 5. /countries with valid JWT token → 200 + array
    // -----------------------------------------------------------------------
    @Test
    public void testGetCountriesWithValidToken() throws Exception {
        // Step 1: obtain a fresh JWT via /authenticate
        MvcResult authResult = mvc.perform(
                get("/authenticate").with(httpBasic("user", "pwd")))
            .andExpect(status().isOk())
            .andReturn();

        // Extract token value from JSON response: {"token":"eyJ..."}
        String responseBody = authResult.getResponse().getContentAsString();
        String token = responseBody.replaceAll(".*\"token\":\"([^\"]+)\".*", "$1");

        // Step 2: call /countries with Bearer token
        ResultActions actions = mvc.perform(
                get("/countries")
                    .header("Authorization", "Bearer " + token));
        actions.andExpect(status().isOk());
        actions.andExpect(jsonPath("$[0].code").exists());
    }

    // -----------------------------------------------------------------------
    // 6. /countries with tampered/invalid JWT → 401 or 403
    // -----------------------------------------------------------------------
    @Test
    public void testGetCountriesWithInvalidToken() throws Exception {
        mvc.perform(get("/countries")
                .header("Authorization", "Bearer this.is.not.a.valid.jwt"))
           .andExpect(result ->
               assertTrue(
                   result.getResponse().getStatus() == 401 ||
                   result.getResponse().getStatus() == 403,
                   "Expected 401 or 403 for invalid JWT"
               )
           );
    }
}
