package com.cognizant.loan.controller;

import com.cognizant.loan.model.Loan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * LoanController exposes the Loan Microservice REST API.
 *
 * Endpoint: GET /loans/{number}
 * Returns a dummy loan response (no backend/DB connectivity).
 *
 * Runs on port 8081 to avoid conflict with the Account service (port 8080).
 * Configure in: src/main/resources/application.properties -> server.port=8081
 */
@RestController
public class LoanController {

    /**
     * Retrieves loan account details for the given loan number.
     *
     * @param number the loan account number from the URL path
     * @return Loan object with number, type, loan amount, emi, and tenure
     */
    @GetMapping("/loans/{number}")
    public ResponseEntity<Loan> getLoan(@PathVariable String number) {
        // Dummy response — no backend connectivity
        Loan loan = new Loan("H00987987972342", "car", 400000, 3258, 18);
        return ResponseEntity.ok(loan);
    }
}
