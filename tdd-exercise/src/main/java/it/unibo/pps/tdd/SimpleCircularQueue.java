package it.unibo.pps.tdd;

public class SimpleCircularQueue implements CircularQueue {

    private final int[] data;
    private int head;
    private int tail;
    private int size;

    public SimpleCircularQueue(final int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be > 0.");
        }
        this.data = new int[capacity];
    }

    @Override
    public void enqueue(final int value) {
        data[tail] = value;

        if (isFull()) {
            head = (head + 1) % data.length; // overwrite oldest
        } else {
            size++;
        }

        tail = (tail + 1) % data.length;
    }

    @Override
    public int dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty.");
        }

        final int value = data[head];
        head = (head + 1) % data.length;
        size--;
        return value;
    }

    @Override
    public int peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty.");
        }
        return data[head];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size == data.length;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int capacity() {
        return data.length;
    }
}