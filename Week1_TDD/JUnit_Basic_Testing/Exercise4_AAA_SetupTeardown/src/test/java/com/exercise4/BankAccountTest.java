package com.exercise4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BankAccountTest {

    private BankAccount account;

    @Before
    public void setUp() {
        account = new BankAccount(1000.0);
    }

    @After
    public void tearDown() {
        account = null;
    }

    @Test
    public void testDeposit() {
        double depositAmount = 500.0;

        account.deposit(depositAmount);

        assertEquals(1500.0, account.getBalance(), 0.001);
    }

    @Test
    public void testWithdraw() {
        double withdrawAmount = 200.0;

        account.withdraw(withdrawAmount);

        assertEquals(800.0, account.getBalance(), 0.001);
    }

    @Test
    public void testGetInitialBalance() {
        double expectedBalance = 1000.0;

        double actualBalance = account.getBalance();

        assertEquals(expectedBalance, actualBalance, 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawInsufficientFunds() {
        double withdrawAmount = 2000.0;

        account.withdraw(withdrawAmount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDepositNegativeAmount() {
        double negativeAmount = -100.0;

        account.deposit(negativeAmount);
    }
}
