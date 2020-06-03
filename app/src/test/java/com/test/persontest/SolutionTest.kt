package com.test.persontest

import junit.framework.TestCase
import org.junit.Test

class SolutionTest : TestCase() {
    private var solution: Solution? = null

    override fun setUp() {
        super.setUp()
        solution = Solution()
    }

    @Test
    fun testFindMedianSortedArrays() {
        solution?.isEquals()
    }

    @Test
    fun testIsNull() {
        solution?.isNull()
    }

    @Test
    fun testCheck() {
        solution?.check2()
    }

    @Test
    fun getData() {
        println(solution?.getData() == null)
        Solution.mHashMap = hashMapOf()
        println(solution?.getData() == null)
    }
}