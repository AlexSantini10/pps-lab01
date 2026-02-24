package it.unibo.pps.model;

/**
 * This class represent a particular instance of a BankAccount.
 * In particular, a Simple Bank Account allows always the deposit of a positive amount
 * while the withdrawal is allowed only if the balance greater or equal the withdrawal amount + a fixed fee.
 */
public class SimpleBankAccount implements BankAccount {

    private double balance;
    private final AccountHolder holder;
    private final double fee;

    public SimpleBankAccount(final AccountHolder holder, final double balance) {
        this(holder, balance, 1);
    }

    public SimpleBankAccount(final AccountHolder holder, final double balance, final double fee) {
        this.holder = holder;
        this.balance = balance;

        if (fee < 0) {
            throw new IllegalArgumentException("Fee must be non-negative");
        }

        this.fee = fee;
    }

    @Override
    public double getBalance() {
        return this.balance;
    }

    @Override
    public void deposit(final int userID, final double amount) {
        if (amount <= 0) {
            return;
        }

        if (checkUser(userID)) {
            this.balance += amount;
        }
    }

    @Override
    public void withdraw(final int userID, final double amount) {
        if (amount <= 0) {
            return;
        }

        if (checkUser(userID) && isWithdrawAllowed(amount)) {
            this.balance -= amount + this.fee;
        }
    }

    private boolean isWithdrawAllowed(final double amount){
        return this.balance >= amount + this.fee;
    }

    private boolean checkUser(final int id) {
        return this.holder.id() == id;
    }
}
