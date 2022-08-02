package com.test.persontest

class MyCircularQueue(k: Int) {
    private var front = 0
    private var rear = 0
    private val capacity = k + 1
    private var elements = IntArray(capacity)

    fun enQueue(value: Int): Boolean {
        return if (isFull()) false else {
            elements[rear] = value
            rear = (rear + 1) % capacity
            true
        }
    }

    fun deQueue(): Boolean {
        return if (isEmpty()) false else {
            front = (front + 1) % capacity
            true
        }
    }

    fun Front(): Int {
        return if (isEmpty()) -1 else elements[front]
    }

    fun Rear(): Int {
        return if (isEmpty()) -1 else elements[(rear - 1 + capacity) % capacity]
    }

    fun isEmpty(): Boolean {
        return front == rear
    }

    fun isFull(): Boolean {
        return ((rear + 1) % capacity) == front
    }

}