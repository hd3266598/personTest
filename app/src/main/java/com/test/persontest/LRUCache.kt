package com.test.persontest

import android.os.Build
import androidx.annotation.RequiresApi

class LRUCache(private val capacity: Int) :
    LinkedHashMap<Int, Int>(capacity, 0.75f, true) {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun get(key: Int): Int {
        return getOrDefault(key, -1)
    }

    override fun put(key: Int, value: Int): Int? {
        return super.put(key, value)
    }

    override fun removeEldestEntry(eldest: MutableMap.MutableEntry<Int, Int>?): Boolean {
        return size > capacity
    }

}