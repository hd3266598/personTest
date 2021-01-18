package com.test.persontest

class MyCircularDeque(k: Int) {
    private var current: Entry<Int>? = null
    private val maxSize = k
    private var curSize = 0

    /** Initialize your data structure here. Set the size of the deque to be k. */

    /** Adds an item at the front of Deque. Return true if the operation is successful. */
    fun insertFront(value: Int): Boolean {
        if (curSize == 0) {
            curSize++
            current = Entry(value)
            return true
        } else if (curSize < maxSize) {
            curSize++
            while (current?.front != null) {
                current = current?.front
            }
            val entry = Entry<Int>(value)
            entry.next = current
            current?.front = entry
            return true
        }
        return false
    }

    /** Adds an item at the rear of Deque. Return true if the operation is successful. */
    fun insertLast(value: Int): Boolean {
        if (curSize == 0) {
            curSize++
            current = Entry(value)
            return true
        } else if (curSize < maxSize) {
            curSize++
            while (current?.next != null) {
                current = current?.next
            }
            val entry = Entry<Int>(value)
            entry.front = current
            current?.next = entry
            return true
        }
        return false
    }

    /** Deletes an item from the front of Deque. Return true if the operation is successful. */
    fun deleteFront(): Boolean {
        while (current?.front != null) {
            current = current?.front
        }
        current?.let {
            current = it.next
            current?.front = null
            curSize--
            return true
        }
        return false
    }

    /** Deletes an item from the rear of Deque. Return true if the operation is successful. */
    fun deleteLast(): Boolean {
        while (current?.next != null) {
            current = current?.next
        }
        current?.let {
            current = it.front
            current?.next = null
            curSize--
            return true
        }
        return false
    }

    /** Get the front item from the deque. */
    fun getFront(): Int {
        while (current?.front != null) {
            current = current?.front
        }
        return current?.value ?: -1
    }

    /** Get the last item from the deque. */
    fun getRear(): Int {
        while (current?.next != null) {
            current = current?.next
        }
        return current?.value ?: -1
    }

    /** Checks whether the circular deque is empty or not. */
    fun isEmpty(): Boolean {
        return curSize == 0
    }

    /** Checks whether the circular deque is full or not. */
    fun isFull(): Boolean {
        return curSize == maxSize
    }


    inner class Entry<T>(var value: Int) {
        var front: Entry<T>? = null
        var next: Entry<T>? = null
    }
}

/**
 * Your MyCircularDeque object will be instantiated and called as such:
 * var obj = MyCircularDeque(k)
 * var param_1 = obj.insertFront(value)
 * var param_2 = obj.insertLast(value)
 * var param_3 = obj.deleteFront()
 * var param_4 = obj.deleteLast()
 * var param_5 = obj.getFront()
 * var param_6 = obj.getRear()
 * var param_7 = obj.isEmpty()
 * var param_8 = obj.isFull()
 */