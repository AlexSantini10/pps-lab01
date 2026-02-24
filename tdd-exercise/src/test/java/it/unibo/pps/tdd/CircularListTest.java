package it.unibo.pps.tdd;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The test suite for testing the CircularQueue implementation
 */
public class CircularListTest {

    @Test
    public void initialState() {
        CircularQueue queue = new SimpleCircularQueue(3);

        assertTrue(queue.isEmpty());
        assertFalse(queue.isFull());
        assertEquals(0, queue.size());
        assertEquals(3, queue.capacity());
        assertThrows(IllegalStateException.class, queue::dequeue);
        assertThrows(IllegalStateException.class, queue::peek);
    }

    @Test
    public void enqueueDequeueScenario() {
        CircularQueue queue = new SimpleCircularQueue(3);

        queue.enqueue(4);
        queue.enqueue(5);
        queue.enqueue(3);

        assertTrue(queue.isFull());
        assertEquals(3, queue.size());
        assertEquals(4, queue.peek());

        assertEquals(4, queue.dequeue());
        assertEquals(5, queue.dequeue());
        assertEquals(3, queue.dequeue());

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        assertThrows(IllegalStateException.class, queue::peek);
    }

    @Test
    public void overwriteWhenFull() {
        CircularQueue queue = new SimpleCircularQueue(3);

        queue.enqueue(4);
        queue.enqueue(5);
        queue.enqueue(3);

        queue.enqueue(7); // overwrites 4

        assertTrue(queue.isFull());
        assertEquals(3, queue.size());
        assertEquals(5, queue.peek());

        assertEquals(5, queue.dequeue());
        assertEquals(3, queue.dequeue());
        assertEquals(7, queue.dequeue());

        assertTrue(queue.isEmpty());
    }

    @Test
    public void circularBehaviorAfterDequeueAndEnqueue() {
        CircularQueue queue = new SimpleCircularQueue(3);

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.peek());

        queue.enqueue(4);

        assertTrue(queue.isFull());
        assertEquals(2, queue.dequeue());
        assertEquals(3, queue.dequeue());
        assertEquals(4, queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    @Test
    public void capacityMustBePositive() {
        assertThrows(IllegalArgumentException.class, () -> new SimpleCircularQueue(0));
        assertThrows(IllegalArgumentException.class, () -> new SimpleCircularQueue(-1));
    }
}