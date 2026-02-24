package it.unibo.pps.tdd;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinMaxStackImplTest {

    @Test
    public void initialStateIsEmpty() {
        MinMaxStack stack = new SimpleMinMaxStack();

        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
        assertThrows(IllegalStateException.class, stack::pop);
        assertThrows(IllegalStateException.class, stack::peek);
        assertThrows(IllegalStateException.class, stack::getMin);
        assertThrows(IllegalStateException.class, stack::getMax);
    }

    @Test
    public void scenarioPushPeekMinMaxPopToEmpty() {
        MinMaxStack stack = new SimpleMinMaxStack();

        stack.push(3);
        assertFalse(stack.isEmpty());
        assertEquals(1, stack.size());
        assertEquals(3, stack.peek());
        assertEquals(3, stack.getMin());
        assertEquals(3, stack.getMax());

        stack.push(1);
        assertEquals(2, stack.size());
        assertEquals(1, stack.peek());
        assertEquals(1, stack.getMin());
        assertEquals(3, stack.getMax());

        stack.push(5);
        assertEquals(3, stack.size());
        assertEquals(5, stack.peek());
        assertEquals(1, stack.getMin());
        assertEquals(5, stack.getMax());

        assertEquals(5, stack.pop());
        assertEquals(2, stack.size());
        assertEquals(1, stack.peek());
        assertEquals(1, stack.getMin());
        assertEquals(3, stack.getMax());

        assertEquals(1, stack.pop());
        assertEquals(1, stack.size());
        assertEquals(3, stack.peek());
        assertEquals(3, stack.getMin());
        assertEquals(3, stack.getMax());

        assertEquals(3, stack.pop());
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
        assertThrows(IllegalStateException.class, stack::peek);
        assertThrows(IllegalStateException.class, stack::getMin);
        assertThrows(IllegalStateException.class, stack::getMax);
    }

    @Test
    public void scenarioHandlesDuplicatesForMinAndMax() {
        MinMaxStack stack = new SimpleMinMaxStack();

        stack.push(2);
        stack.push(2);
        stack.push(2);

        assertEquals(2, stack.getMin());
        assertEquals(2, stack.getMax());
        assertEquals(3, stack.size());

        assertEquals(2, stack.pop());
        assertEquals(2, stack.getMin());
        assertEquals(2, stack.getMax());
        assertEquals(2, stack.size());

        assertEquals(2, stack.pop());
        assertEquals(2, stack.getMin());
        assertEquals(2, stack.getMax());
        assertEquals(1, stack.size());

        assertEquals(2, stack.pop());
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    public void scenarioMinAndMaxChangeAfterPops() {
        MinMaxStack stack = new SimpleMinMaxStack();

        stack.push(4);
        stack.push(1);
        stack.push(3);
        stack.push(2);

        assertEquals(2, stack.peek());
        assertEquals(1, stack.getMin());
        assertEquals(4, stack.getMax());

        assertEquals(2, stack.pop());
        assertEquals(3, stack.peek());
        assertEquals(1, stack.getMin());
        assertEquals(4, stack.getMax());

        assertEquals(3, stack.pop());
        assertEquals(1, stack.peek());
        assertEquals(1, stack.getMin());
        assertEquals(4, stack.getMax());

        assertEquals(1, stack.pop());
        assertEquals(4, stack.peek());
        assertEquals(4, stack.getMin());
        assertEquals(4, stack.getMax());
    }

    @Test
    public void popPeekMinMaxThrowWhenEmptyAfterOperations() {
        MinMaxStack stack = new SimpleMinMaxStack();

        stack.push(10);
        assertEquals(10, stack.pop());

        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
        assertThrows(IllegalStateException.class, stack::pop);
        assertThrows(IllegalStateException.class, stack::peek);
        assertThrows(IllegalStateException.class, stack::getMin);
        assertThrows(IllegalStateException.class, stack::getMax);
    }
}