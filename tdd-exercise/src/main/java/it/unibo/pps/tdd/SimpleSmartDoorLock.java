package it.unibo.pps.tdd;

public class SimpleSmartDoorLock implements SmartDoorLock {

    private int pin;                // The current PIN for the door lock
    private boolean locked;         // Indicates whether the door is currently locked
    private int failedAttempts;     // Counter for failed unlock attempts
    private boolean blocked;        // Indicates whether the lock is in a blocked state
    private final int maxAttempts = 3;  // Maximum allowed failed attempts before blocking

    SimpleSmartDoorLock(){
        this.reset();
    }

    @Override
    public void setPin(int pin) {
        if (this.isLocked() || this.isBlocked()) {
            return;
        }

        // Check if the provided PIN is a valid 4-digit number
        if (pin < 1000 || pin > 9999) {
            return;
        }

        this.pin = pin;
    }

    @Override
    public void unlock(int pin) {
        if (this.isBlocked() || !this.isPinSet() || !this.isLocked()) {
            return;
        }


        if (pin == this.pin) {
            this.locked = false;
            this.failedAttempts = 0; // Reset failed attempts on successful unlock
        } else {
            handleFailedAttempt();
        }
    }

    private boolean isPinSet() {
        return this.pin != 0; // Assuming 0 is an invalid PIN and indicates that the PIN is not set
    }

    @Override
    public void lock() {
        if (!isPinSet()) {
            throw new IllegalStateException("Cannot lock the door without setting a PIN.");
        }

        this.locked = true;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    private void blockIfAttemptsExceeded() {
        if (this.failedAttempts >= this.getMaxAttempts()) {
            this.blocked = true;
        }
    }

    @Override
    public boolean isBlocked() {
        return blocked;
    }

    @Override
    public int getMaxAttempts() {
        return maxAttempts;
    }

    private void handleFailedAttempt() {
        this.failedAttempts++;
        blockIfAttemptsExceeded();
    }

    @Override
    public int getFailedAttempts() {
        return failedAttempts;
    }

    @Override
    public void reset() {
        this.pin = 0; // Reset PIN to default (indicating no PIN set)
        this.failedAttempts = 0; // Reset failed attempts
        this.blocked = false; // Unblock the door after reset
        this.locked = false; // Unlock the door after reset
    }
}
