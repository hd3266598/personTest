package com.test.persontest

import com.test.persontest.person.LiuDeHua
import com.test.persontest.person.StarProxy
import com.test.persontest.person.star
import junit.framework.TestCase
import org.junit.Test
import java.lang.StringBuilder
import kotlin.math.max
import kotlin.math.min

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
//        println(Double.MAX_VALUE)
//        SolutionJava.cc()

//        var type = LineDirectionType.DEFAULT_DIRECTION
//        val type1 = type
//        type = LineDirectionType.LEFT_DIRECTION

        println(0xff)
        println(0x22)
        println(0x324)
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
        for (i in 0..100) {
            list.addAll(intArrayOf)
        }
        val toArray = list.toIntArray()
        val current = System.currentTimeMillis()
        solution?.quickSort(toArray, 0, toArray.lastIndex)
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


    //The grid is:
    //[ [3, 0, 8, 4],
    //  [2, 4, 5, 7],
    //  [9, 2, 6, 3],
    //  [0, 3, 1, 0] ]
    fun maxIncreaseKeepingSkyline(grid: Array<IntArray>): Int {
        val size = grid.size
        val row = IntArray(size)
        val clown = IntArray(size)
        for (i in 0 until size) {
            for (j in 0 until size) {
                row[i] = max(row[i], grid[i][j])
                clown[i] = max(clown[i], grid[j][i])
            }
        }
        var sum = 0
        for (i in 0 until size) {
            for (j in 0 until size) {
                sum += (min(row[i], clown[j]) - grid[i][j])
            }
        }
        return sum
    }

    fun findRadius(houses: IntArray, heaters: IntArray): Int {
        houses.sort()
        heaters.sort()
        if (houses.last() < heaters.last()) {
            return heaters.last() - houses.first()
        }
        val temp = IntArray(houses.last())
        for (i in temp.indices) {
            temp[i] = i + 1
        }
        var sum = 0
        var count = 0
        val array = IntArray(temp.size)
        for (i in array.indices) {
            array[i] = i + 1
        }
        for (i in temp.indices) {
            array[i] = if (heaters.contains(temp[i])) 1 else 0
        }
        for (i in array.indices) {
            if (array[i] == 1) {
                sum = if (count and 1 == 0) {
                    count / 2
                } else {
                    count + 1 / 2
                }
                count = 0
            } else {
                count++
                sum = max(sum, count)
            }
        }
        println(sum)
        return sum
    }

    fun dayOfYear(date: String): Int {
        val year = date.substring(0, 4).toInt()
        val month = date.substring(5, 7).toInt()
        val day = date.substring(8, 10).toInt()
        val months = intArrayOf(31, 28, 31, 30, 31, 30, 31, 30, 31, 30, 31, 30)
        if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
            ++months[1]
        }
        var sum = 0
        for (i in 0 until month - 1) {
            sum += months[i]
        }
        println(sum + day)
        return sum + day
    }

    fun repeatedStringMatch(a: String, b: String): Int {
        val array = b.toCharArray()
        array.forEach {
            if (!a.contains(it)) {
                return -1
            }
        }
        val stringBuilder = StringBuilder(a)
        var count = 1
        val length = (b.length / a.length) + 2
        for (i in 1..length) {
            if (stringBuilder.contains(b)) {
                return count
            } else {
                count++
                stringBuilder.append(a)
            }
        }
        return -1
    }

    @Test
    fun testMain() {
        repeatedStringMatch("abc", "cabcabca")
    }
}