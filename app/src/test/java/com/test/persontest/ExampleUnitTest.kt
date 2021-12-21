package com.test.persontest

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
//        assertEquals(4, 2 + 2)

        var sum = 0.0
        for (i in 1..10) {
            sum += 1.0 / (i)
        }
        sum*=10.0
        println(sum)
    }
}
