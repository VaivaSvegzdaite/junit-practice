package com.vsvegzdaite.junit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MoneyTransactionServiceTest {

    private static final String MONEY_AMOUNT_EXCEPTION_MSG = "Money amount should be greater than 0";
    private static final String ACCOUNT_EXCEPTION_MSG = "Accounts shouldn't be null";
    private static final double RANDOM_MONEY_AMOUNT = 100;
    private static final double ZERO_MONEY_AMOUNT = 0;
    private static final double MORE_THAN_RANDOM_MONEY_AMOUNT = 200;
    private static final double NEGATIVE_MONEY_AMOUNT = -1;

    private MoneyTransactionService testInstance;

    //    @Before junit4
    @BeforeEach
    void setUp() {
        testInstance = new MoneyTransactionService();
    }

    //    @After junit4
    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldTransferMoneyFromOneAccountToAnother() {
        var account1 = new Account(RANDOM_MONEY_AMOUNT);
        var account2 = new Account(ZERO_MONEY_AMOUNT);

        testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT);

        assertEquals(ZERO_MONEY_AMOUNT, account1.getMoneyAmount());
        assertEquals(RANDOM_MONEY_AMOUNT, account2.getMoneyAmount());
    }

    @Test
    void shouldThrowExceptionIfAccountFromIsNull() {
        Account account1 = null;
        Account account2 = new Account(RANDOM_MONEY_AMOUNT);

        var exception = assertThrows(IllegalArgumentException.class,
                () -> testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT));

        assertEquals(ACCOUNT_EXCEPTION_MSG, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionIfAccountToIsNull() {
        Account account1 = new Account(RANDOM_MONEY_AMOUNT);
        Account account2 = null;

        var exception = assertThrows(IllegalArgumentException.class,
                () -> testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT));

        assertEquals(ACCOUNT_EXCEPTION_MSG, exception.getMessage());
    }

    @Test
    void shouldThrowNotEnoughMoneyExceptionWhenTransferMoreMoney() {
        var account1 = new Account(RANDOM_MONEY_AMOUNT);
        var account2 = new Account(MORE_THAN_RANDOM_MONEY_AMOUNT);

        assertThrows(MoneyTransactionService.NotEnoughMoneyException.class,
                () -> testInstance.transferMoney(account1, account2, MORE_THAN_RANDOM_MONEY_AMOUNT));
    }

    @Test
    void shouldThrowExcpetionWhenTransferNegativeAmount() {
        Account account1 = new Account();
        Account account2 = new Account();

        var exception = assertThrows(IllegalArgumentException.class, () ->
                testInstance.transferMoney(account1, account2, NEGATIVE_MONEY_AMOUNT));
        assertEquals(MONEY_AMOUNT_EXCEPTION_MSG, exception.getMessage());
    }

    @Test
    void shouldThrowExcpetionWhenTransferZeroMoneyAmount() {
        Account account1 = new Account();
        Account account2 = new Account();

        var exception = assertThrows(IllegalArgumentException.class, () ->
                testInstance.transferMoney(account1, account2, ZERO_MONEY_AMOUNT));
        assertEquals(MONEY_AMOUNT_EXCEPTION_MSG, exception.getMessage());
    }

    @Test
    void groupedAssertionsExamples() {
        var account1 = new Account(RANDOM_MONEY_AMOUNT);
        var account2 = new Account(ZERO_MONEY_AMOUNT);

        testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT);

        assertAll("Money transaction",
                () -> assertEquals(ZERO_MONEY_AMOUNT, account1.getMoneyAmount()),
                () -> assertEquals(RANDOM_MONEY_AMOUNT, account2.getMoneyAmount())
        );
    }

    @Test
    void dependentAssertionsExample() {
        var account1 = new Account(RANDOM_MONEY_AMOUNT);
        var account2 = new Account(ZERO_MONEY_AMOUNT);

        assertAll("Money transaction",
                () -> {
                    boolean isTransactionSucceed = testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT);
                    assertTrue(isTransactionSucceed);

                    // Executed only if the previous assertion is valid.
                    assertAll("Money amount is changed on the accounts",
                            () -> assertEquals(ZERO_MONEY_AMOUNT, account1.getMoneyAmount()),
                            () -> assertEquals(RANDOM_MONEY_AMOUNT, account2.getMoneyAmount())
                    );
                }
        );
    }

    @Test
    void testWithTimeoutExample() {
        var account1 = new Account(RANDOM_MONEY_AMOUNT);
        var account2 = new Account(ZERO_MONEY_AMOUNT);

        assertTimeout(Duration.ofSeconds(1),
                () -> testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT));
    }


    @Test
    @Timeout(2)
        //	@Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    void timeoutNotExceededWithResult() {
        var account1 = new Account(RANDOM_MONEY_AMOUNT);
        var account2 = new Account(ZERO_MONEY_AMOUNT);

        boolean actualResult = assertTimeout(Duration.ofSeconds(1),
                () -> testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT));
        assertTrue(actualResult);
    }

    @ParameterizedTest
    @ValueSource(ints = {100, 200, 50, -10})
    void parametrizedTestExample(int moneyAmount) {
        assumeTrue(moneyAmount > 0, () -> "Money amount can't be negative");

        var account1 = new Account(moneyAmount);
        var account2 = new Account(ZERO_MONEY_AMOUNT);

        assertTrue(testInstance.transferMoney(account1, account2, moneyAmount));
    }



}