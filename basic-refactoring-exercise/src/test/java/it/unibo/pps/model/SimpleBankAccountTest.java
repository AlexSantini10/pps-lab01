package it.unibo.pps.model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The test suite for testing the SimpleBankAccount implementation
 */
class SimpleBankAccountTest {

    private AccountHolder accountHolder;
    private BankAccount bankAccount;
    private final double fee = 1;

    @BeforeEach
    void beforeEach(){
        accountHolder = new AccountHolder("Mario", "Rossi", 1);
        bankAccount = new SimpleBankAccount(accountHolder, 0, fee);
    }

    @Test
    void testInitialBalance() {
        assertEquals(0, bankAccount.getBalance());
    }

    @Test
    void testDeposit() {
        bankAccount.deposit(accountHolder.id(), 100);
        assertEquals(100, bankAccount.getBalance());
    }

    @Test
    void testWrongDeposit() {
        bankAccount.deposit(accountHolder.id(), 100);
        bankAccount.deposit(2, 50);
        assertEquals(100, bankAccount.getBalance());
    }

    @Test
    void testWithdraw() {
        bankAccount.deposit(accountHolder.id(), 100);
        bankAccount.withdraw(accountHolder.id(), 70);
        assertEquals(100 - 70 - fee, bankAccount.getBalance());
    }

    @Test
    void testWithdrawLimit() {
        bankAccount.deposit(accountHolder.id(), 100);
        bankAccount.withdraw(accountHolder.id(), 99);
        assertEquals(0, bankAccount.getBalance());
    }

    @Test
    void testWrongWithdraw() {
        bankAccount.deposit(accountHolder.id(), 100);
        bankAccount.withdraw(2, 70);
        assertEquals(100, bankAccount.getBalance());
    }

    @Test
    void testWrongWithdrawLimit() {
        bankAccount.deposit(accountHolder.id(), 100);
        bankAccount.withdraw(accountHolder.id(), 100);
        assertEquals(100, bankAccount.getBalance());
    }

    @Test
    void testNegativeDeposit() {
        bankAccount.deposit(accountHolder.id(), 100);
        bankAccount.deposit(accountHolder.id(), -50);
        assertEquals(100, bankAccount.getBalance());
    }

    @Test
    void testNegativeWithdraw() {
        bankAccount.deposit(accountHolder.id(), 100);
        bankAccount.withdraw(accountHolder.id(), -50);
        assertEquals(100, bankAccount.getBalance());
    }
}
