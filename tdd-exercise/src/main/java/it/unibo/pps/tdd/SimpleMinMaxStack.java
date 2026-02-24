package it.unibo.pps.tdd;

import java.util.ArrayDeque;
import java.util.Deque;

public class SimpleMinMaxStack implements MinMaxStack {

    private final Deque<Integer> values = new ArrayDeque<>();
    private final Deque<Integer> mins = new ArrayDeque<>();
    private final Deque<Integer> maxs = new ArrayDeque<>();

    @Override
    public void push(final int value) {
        values.push(value);

        if (mins.isEmpty()) {
            mins.push(value);
        } else {
            final int currentMin = mins.peek();
            mins.push(value < currentMin ? value : currentMin);
        }

        if (maxs.isEmpty()) {
            maxs.push(value);
        } else {
            final int currentMax = maxs.peek();
            maxs.push(value > currentMax ? value : currentMax);
        }
    }

    @Override
    public int pop() {
        if (values.isEmpty()) {
            throw new IllegalStateException("Cannot pop from an empty stack.");
        }
        mins.pop();
        maxs.pop();
        return values.pop();
    }

    @Override
    public int peek() {
        if (values.isEmpty()) {
            throw new IllegalStateException("Cannot peek an empty stack.");
        }
        return values.peek();
    }

    @Override
    public int getMin() {
        if (values.isEmpty()) {
            throw new IllegalStateException("Cannot getMin from an empty stack.");
        }
        return mins.peek();
    }

    @Override
    public int getMax() {
        if (values.isEmpty()) {
            throw new IllegalStateException("Cannot getMax from an empty stack.");
        }
        return maxs.peek();
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public int size() {
        return values.size();
    }
}