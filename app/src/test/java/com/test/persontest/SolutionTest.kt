package com.test.persontest

import com.test.persontest.person.LiuDeHua
import com.test.persontest.person.StarProxy
import com.test.persontest.person.star
import junit.framework.TestCase
import org.junit.Test

class SolutionTest : TestCase() {
    private var solution: Solution? = null
    private var solutionJava: SolutionJava? = null
    private var circularDeque = MyCircularDeque(3); // 设置容量大小为3
    private var codec = Codec()
    private var hashMap = hashMapOf<String, String>()

    override fun setUp() {
        super.setUp()
        solution = Solution()
        solutionJava = SolutionJava()
    }

    @Test
    fun testFindMedianSortedArrays() {
        hashMap.put("1", "1")
        hashMap.put("2", "2")
        hashMap.put("3", "3")
        hashMap.put("4", "4")

//        hashMap.values.removeIf { it == "2" }
//        hashMap.remove("2")
        hashMap.keys.removeIf { it == "2" }
        println(hashMap.size)
//        solution?.isEquals()
    }

    @Test
    fun testIsNull() {
        println(solution?.isPalindrome("A man, a plan, a canal: Panama"))
    }

    @Test
    fun testCheck() {
        codec.buildTree(intArrayOf(3, 9, 20, 15, 7), intArrayOf(9, 3, 15, 20, 7))
    }

    @Test
    fun testGetData() {
        println(solution?.getData() == null)
        Solution.mHashMap = hashMapOf()
        println(solution?.getData() == null)
    }

    @Test
    fun testCheckIn() {
        println(solution?.reverseWords("  hello world!  "))
    }

    @Test
    fun testRemoveDuplicates() {
        println(solution?.removeDuplicates(intArrayOf(1, 1, 1, 2, 2, 2, 3, 3, 3)))
    }

    @Test
    fun testMaxArea() {
        println(solution?.maxArea(intArrayOf(1, 8, 6, 2, 5, 4, 8, 3, 7)))
    }

    @Test
    fun testQuickSort() {
        val list = arrayListOf<Int>()
        val intArrayOf = arrayListOf(9, 8, 6, 2, 5, 4, 8, 3, 7, 2, 3, 4, 6, 1, 3, 8, 45, 93, 435, 26)
        for (i in 0..100){
            list.addAll(intArrayOf)
        }
        val toArray = list.toIntArray()
        val current = System.currentTimeMillis()
        solution?.quickSort(toArray,0,toArray.lastIndex)
        println(System.currentTimeMillis() - current)
        println(toArray.joinToString(","))
    }

    @Test
    fun testProxy() {
        val liuDeHua = LiuDeHua()
        val starProxy = StarProxy()
        starProxy.setTarget(liuDeHua)
        val creatProxyedObj = starProxy.CreatProxyedObj()
        (creatProxyedObj as star).dance("一支舞")
        (creatProxyedObj as star).sing("一首歌舞")
    }
}