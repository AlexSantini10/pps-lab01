package it.unibo.pps.tdd;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SmartDoorLockTest {

    @Test
    public void initialState() {
        SmartDoorLock lock = new SimpleSmartDoorLock();

        assertFalse(lock.isLocked());
        assertFalse(lock.isBlocked());
        assertEquals(0, lock.getFailedAttempts());
        assertEquals(3, lock.getMaxAttempts());
    }

    @Test
    public void lockWithoutPin() {
        SmartDoorLock lock = new SimpleSmartDoorLock();

        assertThrows(IllegalStateException.class, lock::lock);
    }

    @Test
    public void invalidPinNotSet() {
        SmartDoorLock lock = new SimpleSmartDoorLock();

        assertThrows(IllegalArgumentException.class, () -> lock.setPin(999));
        assertThrows(IllegalArgumentException.class, () -> lock.setPin(10000));

        assertThrows(IllegalStateException.class, lock::lock);
    }

    @Test
    public void setPinAndLock() {
        SmartDoorLock lock = new SimpleSmartDoorLock();

        lock.setPin(1234);
        lock.lock();

        assertTrue(lock.isLocked());
    }

    @Test
    public void setPinWhileLockedThrows() {
        SmartDoorLock lock = new SimpleSmartDoorLock();

        lock.setPin(1234);
        lock.lock();

        assertThrows(IllegalStateException.class, () -> lock.setPin(9999));

        lock.unlock(1234);
        assertFalse(lock.isLocked());
    }

    @Test
    public void unlockWhenOpenThrows() {
        SmartDoorLock lock = new SimpleSmartDoorLock();

        lock.setPin(1234);

        assertThrows(IllegalStateException.class, () -> lock.unlock(1234));

        assertFalse(lock.isLocked());
        assertEquals(0, lock.getFailedAttempts());
    }

    @Test
    public void correctUnlockResetsAttempts() {
        SmartDoorLock lock = new SimpleSmartDoorLock();

        lock.setPin(1234);
        lock.lock();

        lock.unlock(1111);
        assertEquals(1, lock.getFailedAttempts());

        lock.unlock(1234);
        assertFalse(lock.isLocked());
        assertEquals(0, lock.getFailedAttempts());
    }

    @Test
    public void wrongUnlockIncrementsAttempts() {
        SmartDoorLock lock = new SimpleSmartDoorLock();

        lock.setPin(1234);
        lock.lock();

        lock.unlock(1111);
        lock.unlock(2222);

        assertTrue(lock.isLocked());
        assertEquals(2, lock.getFailedAttempts());
        assertFalse(lock.isBlocked());
    }

    @Test
    public void tooManyAttemptsBlocks() {
        SmartDoorLock lock = new SimpleSmartDoorLock();

        lock.setPin(1234);
        lock.lock();

        lock.unlock(1111);
        lock.unlock(2222);
        lock.unlock(3333);

        assertTrue(lock.isBlocked());
        assertTrue(lock.isLocked());
        assertEquals(3, lock.getFailedAttempts());
    }

    @Test
    public void blockedCannotUnlockThrows() {
        SmartDoorLock lock = new SimpleSmartDoorLock();

        lock.setPin(1234);
        lock.lock();

        lock.unlock(1111);
        lock.unlock(2222);
        lock.unlock(3333);

        assertThrows(IllegalStateException.class, () -> lock.unlock(1234));

        assertTrue(lock.isBlocked());
        assertTrue(lock.isLocked());
        assertEquals(3, lock.getFailedAttempts());
    }

    @Test
    public void resetRestoresInitialState() {
        SmartDoorLock lock = new SimpleSmartDoorLock();

        lock.setPin(1234);
        lock.lock();
        lock.unlock(1111);
        lock.unlock(2222);
        lock.unlock(3333);

        lock.reset();

        assertFalse(lock.isLocked());
        assertFalse(lock.isBlocked());
        assertEquals(0, lock.getFailedAttempts());
        assertThrows(IllegalStateException.class, lock::lock);
    }
}