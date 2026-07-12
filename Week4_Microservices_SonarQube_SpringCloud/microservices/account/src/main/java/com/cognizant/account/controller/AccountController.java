package com.cognizant.account.controller;

import com.cognizant.account.model.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * AccountController exposes the Account Microservice REST API.
 *
 * Endpoint: GET /accounts/{number}
 * Returns a dummy account response (no backend/DB connectivity).
 */
@RestController
public class AccountController {

    /**
     * Retrieves account details for the given account number.
     *
     * @param number the account number from the URL path
     * @return Account object with number, type, and balance
     */
    @GetMapping("/accounts/{number}")
    public ResponseEntity<Account> getAccount(@PathVariable String number) {
        // Dummy response — no backend connectivity
        Account account = new Account("00987987973432", "savings", 234343);
        return ResponseEntity.ok(account);
    }
}
