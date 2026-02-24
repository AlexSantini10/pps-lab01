package it.unibo.pps.tdd;

public class SimpleSmartDoorLock implements SmartDoorLock {

    private int pin;                    // The current PIN for the door lock
    private boolean locked;             // Indicates whether the door is currently locked
    private int failedAttempts;         // Counter for failed unlock attempts
    private boolean blocked;            // Indicates whether the lock is in a blocked state
    private final int maxAttempts = 3;  // Maximum allowed failed attempts before blocking

    public SimpleSmartDoorLock() {
        this.reset();
    }

    @Override
    public void setPin(int pin) {
        if (this.isLocked()) {
            throw new IllegalStateException(
                    "Cannot set PIN while the door is locked."
            );
        }

        if (this.isBlocked()) {
            throw new IllegalStateException(
                    "Cannot set PIN while the lock is blocked."
            );
        }

        // Check if the provided PIN is a valid 4-digit number
        if (pin < 1000 || pin > 9999) {
            throw new IllegalArgumentException(
                    "PIN must be a 4-digit number between 1000 and 9999."
            );
        }

        this.pin = pin;
    }

    @Override
    public void unlock(int pin) {
        if (this.isBlocked()) {
            throw new IllegalStateException(
                    "Cannot unlock: the lock is blocked."
            );
        }

        if (!this.isPinSet()) {
            throw new IllegalStateException(
                    "Cannot unlock: PIN has not been set."
            );
        }

        if (!this.isLocked()) {
            throw new IllegalStateException(
                    "Cannot unlock: the door is already unlocked."
            );
        }

        if (pin == this.pin) {
            this.locked = false;
            this.failedAttempts = 0;     // Reset failed attempts on success
        } else {
            this.failedAttempts++;
            if (this.failedAttempts >= this.getMaxAttempts()) {
                this.blocked = true;
            }
        }
    }

    private boolean isPinSet() {
        return this.pin != 0;   // 0 means no PIN set
    }

    @Override
    public void lock() {
        if (!isPinSet()) {
            throw new IllegalStateException(
                    "Cannot lock the door without setting a PIN."
            );
        }

        if (this.isBlocked()) {
            throw new IllegalStateException(
                    "Cannot lock: the lock is blocked."
            );
        }

        this.locked = true;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public boolean isBlocked() {
        return blocked;
    }

    @Override
    public int getMaxAttempts() {
        return maxAttempts;
    }

    @Override
    public int getFailedAttempts() {
        return failedAttempts;
    }

    @Override
    public void reset() {
        this.pin = 0;               // No PIN set
        this.failedAttempts = 0;    // Reset failed attempts
        this.blocked = false;       // Remove blocked state
        this.locked = false;        // Initial state is open
    }
}