package com.test.persontest

import android.util.SparseArray
import androidx.core.util.containsValue
import com.test.persontest.person.LiuDeHua
import com.test.persontest.person.StarProxy
import com.test.persontest.person.star
import junit.framework.TestCase
import org.junit.Test
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
        val intArrayOf =
            arrayListOf(9, 8, 6, 2, 5, 4, 8, 3, 7, 2, 3, 4, 6, 1, 3, 8, 45, 93, 435, 26)
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

    fun simplifiedFractions(n: Int): List<String> {
        val ans: MutableList<String> = ArrayList()
        for (a in 2..n) {
            for (b in 1 until a) {
                if (gcd(a, b) == 1) {
                    ans.add("$b/$a")
                }
            }
        }
        return ans
    }

    fun gcd(a: Int, b: Int): Int {
        return if (b == 0) a else gcd(b, a % b)
    }

    fun minimumDifference(nums: IntArray, k: Int): Int {
        nums.sort()
        var d = Int.MAX_VALUE
        for (i in 0..nums.size - k) {
            d = d.coerceAtMost(Math.abs(nums[i + k - 1] - nums[i]))
        }
        return d
    }

    fun singleNonDuplicate(nums: IntArray): Int {
        var l = 0
        var r = nums.lastIndex
        while (l < r) {
            val mid = (l + r) / 2
            if (nums[mid] == nums[mid xor 1]) {
                l = mid + 1
            } else {
                r = mid
            }
        }
        return nums[l]
    }

    fun addDigits(num: Int): Int {
        return (num - 1) % 9 + 1
    }

    fun subArrayRanges(nums: IntArray): Long {
        val n = nums.lastIndex
        var sum = 0L
        for (i in 0..n) {
            var min = Int.MAX_VALUE
            var max = Int.MIN_VALUE
            for (j in i..n) {
                max = Math.max(max, nums[j])
                min = Math.min(min, nums[j])
                sum += max - min
            }
        }
        return sum
    }

    fun convertToBase7(num: Int): String {
        val flag = num < 0
        var n = if (flag) -num else num
        val string = StringBuilder()
        do {
            string.append(n % 7)
            n /= 7
        } while (n != 0)
        string.reverse()
        return "${if (flag) "-" else ""}$string"
    }

    fun findRestaurant(list1: Array<String>, list2: Array<String>): Array<String> {
        val sparseArray = SparseArray<String>()
        list1.forEachIndexed { index, s ->
            sparseArray.put(index, s)
        }
        var max = Int.MAX_VALUE
        val array = arrayListOf<String>()
        list2.forEachIndexed { index, s ->
            if (sparseArray.containsValue(s)) {
                val i = sparseArray.keyAt(sparseArray.indexOfValue(s)) + index
                if (i < max) {
                    max = i
                    array.clear()
                    array.add(s)
                } else if (i == max) {
                    array.add(s)
                }
            }
        }
        array.sort()
        return array.toArray(Array(array.size) {
            return@Array ""
        })
    }

    fun trailingZeroes(n: Int): Int {
        var count = 0
        var nn = n
        while (nn > 0) {
            nn /= 5
            count += nn
        }
        return count
    }


    @Test
    fun testMain() {
        solutionJava?.cc()
    }
}