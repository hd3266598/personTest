package com.test.persontest

open class Node(var `val`: Int) {
    var children: List<Node?> = listOf()
    var code = 0

    init {
        android.util.Log.i("Node", ":初始化Node ")
    }
}