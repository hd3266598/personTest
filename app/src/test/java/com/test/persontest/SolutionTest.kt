package com.test.persontest

import android.graphics.Matrix
import android.util.SparseArray
import androidx.core.util.containsValue
import com.test.persontest.person.LiuDeHua
import com.test.persontest.person.StarProxy
import com.test.persontest.person.star
import junit.framework.TestCase
import org.junit.Test
import java.util.*
import kotlin.Comparator
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap
import kotlin.math.abs
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
//        codec.buildTree(intArrayOf(3, 9, 20, 15, 7), intArrayOf(9, 3, 15, 20, 7))
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
        list.addAll(intArrayOf)
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
        creatProxyedObj.sing("一首歌舞")
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

    fun findDiagonalOrder(mat: Array<IntArray>): IntArray {
        val m = mat.size
        val n = mat[0].size
        val res = IntArray(m * n)
        var pos = 0
        for (i in 0 until m + n) {
            //偶数
            if (i and 1 == 0) {
                var x = if (i < m) i else m - 1
                var y = if (i < m) 0 else i - m + 1
                while (y < n && x >= 0) {
                    res[pos] = mat[x][y]
                    pos++
                    x--
                    y++
                }
            } else {
                var x = if (i < n) 0 else i - n + 1
                var y = if (i < n) i else n - 1
                while (x < m && y >= 0) {
                    res[pos] = mat[x][y]
                    pos++
                    x++
                    y--
                }
            }
        }
        return res
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

    fun countNumbersWithUniqueDigits(n: Int): Int {
        if (n == 0) return 1
        if (n == 1) return 10
        var res = 10
        var cur = 9
        for (i in 0 until n - 1) {
            cur *= 9 - i
            res += cur
        }
        return res
    }

    fun shortestToChar(s: String, c: Char): IntArray {
        val n = s.length
        val intArray = IntArray(n)
        var cx = -n
        for (i in 0 until n) {
            if (s[i] == c) {
                cx = i
            }
            intArray[i] = i - cx
        }
        var cx2 = 2 * n
        for (i in n - 1 downTo 0) {
            if (s[i] == c) {
                cx2 = i
            }
            intArray[i] = Math.min(intArray[i], cx2 - i)
        }
        return intArray
    }

    fun maxRotateFunction(nums: IntArray): Int {
        var result = 0
        val sums = nums.sum()
        for (i in nums.indices) {
            result += i * nums[i]
        }
        var answer = result
        val size = nums.size
        for (i in 1 until size) {
            result += sums - size * nums[size - i]
            answer = Math.max(answer, result)
        }
        return answer
    }

    fun findDuplicates(nums: IntArray): List<Int> {
        val result = arrayListOf<Int>()
        nums.forEach {
            val key = Math.abs(it)
            if (nums[key - 1] > 0) {
                nums[key - 1] = -nums[key - 1]
            } else {
                result.add(key)
            }
        }
        return result
    }

    fun diStringMatch(s: String): IntArray {
        val result = IntArray(s.length + 1)
        var lo = 0
        var hi = s.length
        s.forEachIndexed { index, c ->
            result[index] = if (c == 'I') lo++ else hi--
        }
        result[s.length] = lo
        return result
    }

    fun romanToInt(s: String): Int {
        val map = hashMapOf<Char, Int>()
        map['I'] = 1
        map['V'] = 5
        map['X'] = 10
        map['L'] = 50
        map['C'] = 100
        map['D'] = 500
        map['M'] = 1000

        var result = 0
        s.forEachIndexed { index, c ->
            val i = map[c]!!
            if (index < s.length - 1 && i < map[s[index + 1]]!!) {
                result -= i
            } else {
                result += i
            }
        }

        return result
    }

    fun frequencySort(nums: IntArray): IntArray {
        val map = hashMapOf<Int, Int>()
        nums.forEach {
            var value = map[it]
            if (value == null) {
                map[it] = 0
            } else {
                map[it] = ++value
            }
        }
        val list = nums.toList()
        val res = list.sortedWith { o1, o2 ->
            val orDefault = map.getOrDefault(o1, 0)
            val orDefault1 = map.getOrDefault(o2, 0)
            if (orDefault == orDefault1) o2 - o1 else {
                orDefault - orDefault1
            }
        }
        return res.toIntArray()
    }

    class Codec() {
        // Encodes a URL to a shortened URL.
        fun serialize(root: TreeNode?): String {
            val list = arrayListOf<Int>()
            postOrder(root, list)
            val s = list.toString()
            return s.substring(1, s.length - 1)
        }

        // Decodes your encoded data to tree.
        fun deserialize(data: String): TreeNode? {
            if (data.isEmpty()) {
                return null
            }
            val stack = Stack<Int>()
            data.split(", ").forEach {
                stack.push(it.toInt())
            }
            return construct(Int.MIN_VALUE, Int.MAX_VALUE, stack)
        }

        private fun postOrder(treeNode: TreeNode?, list: ArrayList<Int>) {
            if (treeNode == null) return
            postOrder(treeNode.left, list)
            postOrder(treeNode.right, list)
            list.add(treeNode.`val`)
        }

        private fun construct(low: Int, high: Int, stack: Stack<Int>): TreeNode? {
            if (stack.isEmpty() || stack.peek() < low || stack.peek() > high) {
                return null
            }
            val value = stack.pop()
            val treeNode = TreeNode(value)
            treeNode.right = construct(value, high, stack)
            treeNode.left = construct(low, value, stack)
            return treeNode
        }

    }

    fun minDeletionSize(strs: Array<String>): Int {
        var result = 0
        val n = strs.first().length
        for (i in 0 until n) {
            for (j in 1 until strs.size) {
                if (strs[j][i] < strs[j - 1][i]) {
                    result++
                    break
                }
            }
        }
        return result
    }

    fun oneEditAway(first: String, second: String): Boolean {
        val m = first.length
        val n = second.length
        return when {
            m - n == 1 -> compare(second, first)
            n - m == 1 -> compare(first, second)
            m == n -> {
                var equals = false
                first.forEachIndexed { index, c ->
                    if (c != second[index]) {
                        if (equals) {
                            return false
                        } else {
                            equals = true
                        }
                    }
                }
                true
            }
            else -> false
        }
    }

    private fun compare(short: String, long: String): Boolean {
        var i = 0
        var j = 0
        while (i < short.length && j < long.length) {
            if (short[i] == long[j]) {
                i++
            }
            j++
            if (j - i > 1) return false
        }
        return true
    }

    fun duplicateZeros(arr: IntArray) {
        var top = 0
        var i = -1
        val n = arr.size
        while (top < n) {
            i++
            if (arr[i] == 0) top++
            top++
        }

        if (top > n) {
            top -= 2
            arr[top] = 0
            top--
            i--
        } else {
            top--
        }

        while (top >= 0) {
            arr[top] = arr[i]
            top--
            if (arr[i] == 0) {
                arr[top] = 0
                top--
            }
            i--
        }
    }

    fun findBottomLeftValue(root: TreeNode?): Int {
        var res = 0
        val queue = ArrayDeque<TreeNode>()
        queue.offer(root)
        while (queue.isNotEmpty()) {
            val node = queue.poll()
            if (node?.right != null) {
                queue.offer(node.right)
            }
            if (node?.left != null) {
                queue.offer(node.left)
            }
            res = node?.`val` ?: 0
        }
        return res
    }

    fun replaceWords(dictionary: List<String>, sentence: String): String {
        val mDictionary = dictionary.toHashSet()
        val split = sentence.split(" ").toMutableList()
        for (i in 0..split.lastIndex) {
            val word = split[i]
            for (j in 0..word.lastIndex) {
                val substring = word.substring(0, 1 + j)
                if (mDictionary.contains(substring)) {
                    split[i] = substring
                    break
                }
            }
        }
        return split.joinToString(" ")
    }

    fun asteroidCollision(asteroids: IntArray): IntArray {
        val stack = Stack<Int>()
        for (it in asteroids) {
            if (it > 0) {
                stack.push(it)
            } else {
                var alive = true
                while (alive && stack.isNotEmpty() && stack.peek() > 0) {
                    alive = stack.peek() < Math.abs(it)
                    if (stack.peek() <= abs(it)) stack.pop()
                }
                if (alive) stack.push(it)
            }
        }
        return stack.toIntArray()
    }

    fun intersectionSizeTwo(intervals: Array<IntArray>): Int {

        return 0
    }

    fun generateTheString(n: Int): String {
        val string = String(CharArray(n - 1) { return@CharArray 's' })
        return if (n and 1 == 0) {
            string + 'b'
        } else string + 's'
    }

    fun minSubsequence(nums: IntArray): List<Int> {
        val ans = arrayListOf<Int>()
        val sum = nums.sum()
        var cur = 0
        nums.sort()
        for (i in nums.lastIndex downTo 0) {
            cur += nums[i]
            ans.add(nums[i])
            if (sum - cur < cur) break
        }
        return ans
    }

    fun addOneRow(root: TreeNode?, `val`: Int, depth: Int): TreeNode? {
        if (depth == 1) {
            val treeNode = TreeNode(`val`)
            treeNode.left = root
            return treeNode
        }
        val list = LinkedList<TreeNode>()
        root?.let { list.offer(it) }
        var cur = 0
        while (list.isNotEmpty()) {
            cur++
            for (i in list.indices) {
                val node = list.poll()
                if (depth - 1 == cur) {
                    val treeNode = TreeNode(`val`)
                    treeNode.left = node?.left
                    node?.left = treeNode
                    val treeNode1 = TreeNode(`val`)
                    treeNode1.right = node?.right
                    node?.right = treeNode1
                } else {
                    if (node?.left != null) list.offer(node.left)
                    if (node?.right != null) list.offer(node.right)
                }
            }
        }
        return root
    }

    fun trimMean(arr: IntArray): Double {
        arr.sort()
        val n = arr.size
        var sum = 0
        for (i in n / 20 until n * 19 / 20) {
            sum += arr[i]
        }
        return sum / (n * 0.9)
    }


    fun maxLengthBetweenEqualCharacters(s: String): Int {
        val array = IntArray(26) { return@IntArray -1 }
        var max = -1
        s.forEachIndexed { index, c ->
            if (array[c - 'a'] == -1) {
                array[c - 'a'] = index
            } else {
                max = max.coerceAtLeast(index - array[c - 'a'] - 1)
            }
        }
        return max
    }


    @Test
    fun testMain() {
        println(frequencySort(intArrayOf(2, 3, 1, 3, 2)).joinToString(","))
    }


    companion object {
        @JvmStatic
        var a: String? = "a"
    }
}