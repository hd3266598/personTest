/**
 * FileName: Solution
 * Author: huangda
 * Date: 2019/11/11 14:14
 * Description: test
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
package com.test.persontest

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.math.BigInteger
import java.util.*
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.LinkedBlockingQueue
import kotlin.math.E
import kotlin.math.max
import kotlin.math.min
import kotlin.system.measureTimeMillis

/**
 * @ClassName: Solution
 * @Description: java类作用描述
 * @Author: huangda
 * @Date: 2019/11/11 14:14
 */
class Solution : CoroutineScope by MainScope() {
    val realCount = 0
    var content: Double? = 88.657

    @Volatile
    var count = 0

//    fun reverseWords(s: String): String {
//        return s.split(" ").filter { it.isNotEmpty() }.reversed().joinToString(" ")
//    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun firstUniqChar(s: String): Int {
        val arrayListOf = arrayListOf<Int>()
        arrayListOf.toList()
        val localHashMap = hashMapOf<Char, Int?>()
        s.forEachIndexed { index, c ->
            localHashMap[c] = localHashMap.getOrDefault(c, 0)?.plus(1)
        }
        s.forEachIndexed { index, c ->
            if (localHashMap[c] == 1) {
                return index
            }
        }
        return 0
    }

    fun fib(N: Int): Int {
        if (N <= 1) return N
        var f = 0
        var s = 1
        var sum = 0
        for (i in 1 until N) {
            sum = f + s
            f = s
            s = sum
        }
        return sum
    }

    fun hammingWeight(n: Int): Int {
        var sum = 0
        var _n = n
        while (_n != 0) {
            sum++
            _n = _n and (_n - 1)
        }
        return sum
    }

    fun isPowerOfTwo(n: Int): Boolean {
        return n > 0 && n and (n - 1) == 0
    }

    fun reverseBits(n: Int): Int {
        var sum = 0
        var _n = n
        for (i in 0 until 32) {
            sum = (sum shl 1) + (_n and 1)
            _n = _n shr 1
        }
        return sum
    }

    fun countBits(num: Int): IntArray {
        val array = IntArray(num + 1)
        array[0] = 0
        for (i in 1..num) {
            var size = 0
            var _i = i
            while (_i != 0) {
                size++
                _i = _i and (_i - 1)
            }
            array[i] = size
        }
        return array
    }

    //"abcabcbb"
    fun lengthOfLongestSubstring(s: String): Int {
        var maxSize = 0
        if (s.isNotEmpty()) {
            maxSize = 1
            for (i in s.indices) {
                var f = true
                for (j in i + 1 until s.length) {
                    if (!f) {
                        break
                    }
                    for (k in i until j) {
                        if (s[k] == s[j]) {
                            f = false
                        }
                    }
                    if (f) {
                        if (maxSize < j - i + 1) {
                            maxSize = j - i + 1
                        }
                    }
                }
            }
        }
        return maxSize
    }

    //输入: ["flower","flow","flight"]
    //输出: "fl"
    fun longestCommonPrefix(strs: Array<String>): String {
        var s = StringBuilder()
        if (strs.isNotEmpty()) {
            var c: Char
            var b = true
            for (j in strs[0].indices) {
                if (strs[0].isEmpty()) {
                    b = false
                }
                if (!b) {
                    break
                }
                c = strs[0][j]
                s.append(c)
                for (k in 1 until strs.size) {
                    if (!strs[k].startsWith(s)) {
                        b = false
                        if (s.last() == c) {
                            s = s.deleteCharAt(s.lastIndex)
                        }
                    }
                }
            }
        }
        return s.toString()
    }

    //输入: s1 = "ab" s2 = "eidbaooo"
    //输出: True
    //解释: s2 包含 s1 的排列之一 ("ba").
    fun checkInclusion(s1: String, s2: String): Boolean {
        s1.toInt()
        val child = if (s1.length > s2.length) s2.toCharArray() else s1.toCharArray()
        val parent = if (s1.length > s2.length) s1.toCharArray() else s2.toCharArray()
        for (i in child.indices) {
            child[0] = child[i].also { child[i] = child[0] }
            if (swap(child, s2)) {
                return true
            } else {
                child[i] = child[0].also { child[0] = child[i] }
            }
        }
        return false
    }

    //输入: "the sky is blue"
    //输出: "blue is sky the"
    fun reverseWords(s: String): Int {
        var lo = 2
        return lo.inv() // value not present

//        return s.split(" ").filter { it.isNotEmpty() }.reversed().joinToString(" ")
    }

    //"25525511135"
    //有效的 IP 地址正好由四个整数（每个整数位于 0 到 255 之间组成），整数之间用 '.' 分隔
//    fun restoreIpAddresses(s: String): List<String> {
//        for (i in s.indices) {
//            if (s[i].toInt() > 2) {
//                break
//            }
//            for (j in i + 1 until s.length) {
//
//            }
//        }
//    }

    fun mySqrt(x: Int): Int {
        var l = 0
        var r = x
        var ans = -1
        while (l <= r) {
            val mid = l + (r - l) / 2
            if (mid.toLong() * mid <= x) {
                ans = mid
                l = mid + 1
            } else {
                r = mid - 1
            }
        }
        return ans
    }


    fun multiply(num1: String, num2: String): String {
        return (BigInteger.valueOf(num1.toLong()) * BigInteger.valueOf(num2.toLong())).toString()
    }


    fun swap(child: CharArray, paret: String): Boolean {
        return paret.indexOf(child.joinToString()) > 0
    }

    fun isEquals() {
        var a = 10
        val b = a
        a = 11
        print("$a||$b")
    }

    fun isNull() {
        val h1 = TestModel("1")
        val h2 = TestModel(1)
        println(h1)
        println(h2)
    }

    fun check() {
        val mutex = Mutex()
        runBlocking {
            withContext(Dispatchers.IO) {
                massiveRun {
                    mutex.withLock {
                        count++
                    }
                }
            }
            println("Counter = $count")
        }
    }

    fun testCheckBoolean(num: Int) {
        println(num in (1 until 10).reversed())
    }

    fun moveZeroes(nums: IntArray): Unit {
        var j = 0
        nums.forEachIndexed { index, i ->
            if (i != 0) {
                val temp = nums[index]
                nums[index] = 0
                nums[j] = temp
                j++
            }
        }
    }

    private suspend fun massiveRun(action: suspend () -> Unit) {
        val measureTimeMillis = measureTimeMillis {
            coroutineScope {
                repeat(100) {
                    launch {
                        println(Thread.currentThread().name)
                        repeat(1000) {
                            action()
                        }
                    }
                }
            }
        }
        println("allTime:$measureTimeMillis")
    }

    fun getData(): HashMap<String, String>? {
        return hashMap
    }

    operator fun invoke() {

    }

    fun check2(list: List<Int>): Int {
        list.forEach {
            println(it)
            return it
        }
        return 0
    }

    fun localPrint() {
        println("我还能打印出来$content||${content?.toInt()}")
    }

    fun search(nums: IntArray, target: Int): Int {
        return if (nums.isEmpty()) {
            -1
        } else {
            var left = 0
            var right = nums.lastIndex
            while (left <= right) {
                val mid = left + (right - left) / 2
                when {
                    nums[mid] == target -> {
                        mid
                    }
                    nums[mid] < target -> {
                        left = mid + 1
                    }
                    else -> {
                        right = mid - 1
                    }
                }
            }
            -1
        }
    }

    fun removeDuplicates(nums: IntArray): Int {
        if (nums.isEmpty()) return 0
        var i = 0
        for (j in 1 until nums.size) {
            if (nums[j] != nums[i]) {
                i++
                nums[i] = nums[j]
            }
        }
        println(nums.toString())
        return i + 1
    }

    fun maxArea(height: IntArray): Int {
        var maxArea = 0
        for (i in height.indices) {
            for (j in 1..height.lastIndex) {
                val area = (j - i) * Math.min(height[i], height[j])
                if (maxArea == 0 || maxArea < area) {
                    maxArea = area
                }
            }
        }
        return maxArea
    }

    fun jump(nums: IntArray): Int {
        var step = 0
        if (nums.isEmpty()) return step
        var end = 0
        var max = 0
        for (i in 0 until nums.lastIndex) {
            max = Math.max(max, nums[i] + i)
            if (i == end) {
                end = max
                step++
            }
        }
        return step
    }

    fun fourSum(nums: IntArray, target: Int): List<List<Int>> {
        val list = arrayListOf<List<Int>>()
        if (nums.size < 4) return list
        Arrays.sort(nums)
        for (i in 0 until nums.size - 3) {
            if (i > 0 && nums[i] == nums[i - 1]) continue
            if (nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3] > target) break
            if (nums[i] + nums[nums.lastIndex] + nums[nums.lastIndex - 1] + nums[nums.lastIndex - 2] < target) continue
            for (j in i + 1 until nums.size - 2) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue
                if (nums[i] + nums[j] + nums[j + 1] + nums[j + 2] > target) break
                if (nums[i] + nums[j] + nums[nums.lastIndex] + nums[nums.lastIndex - 1] < target) continue
                var l = j + 1
                var r = nums.lastIndex
                while (l < r) {
                    val sum = nums[i] + nums[j] + nums[l] + nums[r]
                    if (sum == target) {
                        list.add(listOf(nums[i], nums[j], nums[l], nums[r]))
                        while (l < r && nums[l] == nums[l + 1]) l++
                        while (l < r && nums[r] == nums[r - 1]) r--
                        l++
                        r--
                    } else if (sum < target) {
                        l++
                    } else if (sum > target) {
                        r--
                    }
                }
            }
        }
        return list
    }

    fun isPalindrome(x: Int): Boolean {
        if (x < 0 || (x > 0 && x % 10 == 0)) return false
        var mX = x
        var reversNumber = 0
        while (mX > reversNumber) {
            reversNumber = reversNumber * 10 + mX % 10
            mX /= 10
        }
        return mX == reversNumber || mX == reversNumber / 10
    }

    fun validMountainArray(A: IntArray): Boolean {
        if (A.size < 3) return false
        var l = 0
        var r = A.lastIndex
        while (l < r) {
            if (A[l] < A[r]) {
                if (A[l] > A[l + 1]) {
                    return false
                } else {
                    l++
                }
            } else {
                if (A[r] > A[r - 1]) {
                    return false
                } else {
                    r--
                }
            }
        }
        return true
    }


    fun largestRectangleArea(heights: IntArray): Int {
        var area = 0
        for (i in heights.indices) {
            var minHeight = Int.MAX_VALUE
            for (j in i..heights.lastIndex) {
                minHeight = Math.min(minHeight, heights[j])
                area = Math.max(area, minHeight * (j - i + 1))
            }
        }
        return area
    }

    fun isAnagram(s: String, t: String): Boolean {
//        val toCharArray = s.toCharArray()
//        val toCharArray1 = t.toCharArray()
//        Arrays.sort(toCharArray)
//        Arrays.sort(toCharArray1)
//        return toCharArray.contentEquals(toCharArray1)

        if (s.length != t.length) return false
        val array = IntArray(26)
        s.forEachIndexed { index, c ->
            array[c - 'a']++
            array[t[index] - 'a']--
        }
        return array.none { it != 0 }
    }


    fun preorderTraversal(root: TreeNode?): List<Int> {
        val list = arrayListOf<Int>()
        inOrder(root, list)
        return list
    }

    fun inOrder(treeNode: TreeNode?, list: ArrayList<Int>) {
        if (treeNode == null) return
        list.add(treeNode.`val`)
        inOrder(treeNode.left, list)
        inOrder(treeNode.right, list)
    }

    fun postorder(root: Node?): List<Int> {
        val stack = Stack<Node?>()
        val outPut = LinkedList<Int>()
        stack.push(root)
//        while (stack.isNotEmpty()) {
//            stack.pop()?.let { it ->
//                outPut.addFirst(it.`val`)
//                it.children.forEach {
//                    stack.push(it)
//                }
//            }
//        }
//        return outPut
        stack.push(root)
        while (stack.isNotEmpty()) {
            stack.pop()?.let { it ->
                outPut.add(it.`val`)
                Collections.reverse(it.children)
                it.children.forEach {
                    it?.let { it1 -> stack.push(it1) }
                }
            }
        }
        return outPut
    }

    fun levelOrder(root: Node?): List<List<Int>> {
        val stack = LinkedList<Node>()
        val out = arrayListOf<ArrayList<Int>>()
        root?.let { stack.add(it) }
        while (stack.isNotEmpty()) {
            val list = arrayListOf<Int>()
            for (i in stack.indices) {
                stack.pop()?.let {
                    list.add(it.`val`)
                    it.children.forEach { _node ->
                        _node?.let { stack.add(it) }
                    }
                }
            }
            out.add(list)
        }
        return out
    }


    fun maxDepth(root: TreeNode?): Int {
        if (root == null) return 0
        val maxLeft = maxDepth(root.left)
        val maxRight = maxDepth(root.right)
        return max(maxLeft, maxRight) + 1
    }

    fun minDepth(root: TreeNode?): Int {
        val list = LinkedList<TreeNode>()
        var ans = 0
        root?.let {
            ans++
            list.add(it)
        }
        while (list.isNotEmpty()) {
            for (i in list.indices) {
                list.poll()?.let { _node ->
                    if (_node.left == null && _node.right == null) {
                        return ans
                    }
                    _node.left?.let { list.add(it) }
                    _node.right?.let { list.add(it) }
                }
            }
            ans++
        }
        return ans
    }

    fun groupAnagrams(strs: Array<String>): List<List<String>> {
        val map = hashMapOf<String, ArrayList<String>>()
        strs.forEach { it ->
            val toCharArray = it.toCharArray()
            Arrays.sort(toCharArray)
            val key = String(toCharArray)
            map.takeIf { !it.containsKey(key) }?.put(key, arrayListOf())
            map[key]?.add(it)
        }
        return map.values.toList()
    }


    fun getLeastNumbers(arr: IntArray, k: Int): IntArray {
        Arrays.sort(arr)
        return arr.copyOf(k)
    }

    fun twoSum(nums: IntArray, target: Int): IntArray {


        val map = hashMapOf<Int, Int>()
        nums.forEachIndexed { index, i ->
            if (map.containsKey(target - i)) {
                return intArrayOf(map[target - i] ?: 0, index)
            } else {
                map[i] = index
            }
        }
        return intArrayOf()
    }

    fun rotate(nums: IntArray, k: Int): Unit {
        var mk = k % nums.size
        reverse(nums, 0, nums.lastIndex)
        reverse(nums, 0, k - 1)
        reverse(nums, k - 1, nums.lastIndex)
    }

    fun reverse(nums: IntArray, start: Int, end: Int) {
        ForkJoinPool()
        var mStart = start
        var mEnd = end
        while (mStart < mEnd) {
            val temp = nums[mStart]
            nums[mStart] = nums[mEnd]
            nums[mEnd] = temp
            mStart++
            mEnd--
        }
    }

    fun isPalindrome(s: String): Boolean {
        var i = 0
        var j = s.length - 1
        while (i < j) {
            while (i < j && !Character.isLetterOrDigit(s[i])) i++
            while (i < j && !Character.isLetterOrDigit(s[j])) j--
            if (!s[i].equals(s[j], true)) {
                return false
            }
            i++
            j--
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun topKFrequent(nums: IntArray, k: Int): IntArray {
        val map = hashMapOf<Int, Int>()
        nums.forEach {
            map[it] = map.getOrDefault(it, 0) + 1
        }
        val priorityQueue = PriorityQueue(map.size, Comparator<IntArray> { o1, o2 -> o2.size - o1.size })
        map.entries.forEach { entry ->
            val intArray = IntArray(entry.value) { entry.key }
            priorityQueue.add(intArray)
        }
        val out = IntArray(k)
        for (i in 0 until k) {
            priorityQueue.peek()?.let {
                out[i] = priorityQueue.poll()[0]
            }
        }
        return out
    }

    fun nthUglyNumber(n: Int): Int {
//        val priorityQueue = PriorityQueue<Int>(n) { o1, o2 -> o2 - o1 }
//        var i = 1
//        while (priorityQueue.size < n) {
//            if (isUgly(i)) {
//                priorityQueue.add(i)
//            }
//            i++
//        }
//        return priorityQueue.poll()
        var a = 0
        var b = 0
        var c = 0
        val out = IntArray(n)
        out[0] = 1
        for (i in 1 until n) {
            val i1 = out[a] * 2
            val i2 = out[b] * 3
            val i3 = out[c] * 5
            out[i] = Math.min(i1, Math.min(i2, i3))
            if (i1 == out[i]) a++
            if (i2 == out[i]) b++
            if (i3 == out[i]) c++
        }
        return out.last()
    }

    fun isUgly(num: Int): Boolean {
        var num = num
        if (num <= 0) {
            return false
        }
        while (true) {
            if (num == 1 || num == 2 || num == 3 || num == 5) {
                return true
            }
            num /= if (num % 2 == 0) {
                2
            } else if (num % 3 == 0) {
                3
            } else if (num % 5 == 0) {
                5
            } else {
                return false
            }
        }
    }

    fun levelOrder(root: TreeNode?): List<List<Int>> {
        val queue = LinkedBlockingQueue<TreeNode>()
        val list = arrayListOf<ArrayList<Int>>()
        root?.let { queue.offer(it) }
        while (queue.isNotEmpty()) {
            val child = arrayListOf<Int>()
            val size = queue.size
            for (i in 0 until size) {
                queue.poll()?.let { it ->
                    child.add(it.`val`)
                    it.left?.let { queue.offer(it) }
                    it.right?.let { queue.offer(it) }
                }
            }
            list.add(child)
        }
        return list
    }

    fun canJump(nums: IntArray): Boolean {
        if (nums.isEmpty()) {
            return false
        }
        nums.forEachIndexed { index, i ->

        }
        var end = nums.lastIndex
        for (i in end downTo 0) {
            if (nums[i] + i >= end) {
                end = i
            }
        }
        return end == 0
    }

    fun uniquePaths(m: Int, n: Int): Int {
//        val array = Array(m) { IntArray(n) }
//        for (i in 0 until m) array[i][n - 1] = 1
//        for (i in 0 until n) array[m - 1][i] = 1
//
//        for (i in 2..m) {
//            for (j in 2..n) {
//                array[m - i][n - j] = array[m - i + 1][n - j] + array[m - i][n - j + 1]
//            }
//        }
//        return array[0][0]

        val array = IntArray(n)
        Arrays.fill(array, 1)
        for (i in 1 until m) {
            for (j in 1 until n) {
                array[j] += array[j - 1]
            }
        }
        return array.last()
    }

    fun uniquePathsWithObstacles(obstacleGrid: Array<IntArray>): Int {
        if (obstacleGrid.isEmpty()) return 0
        val m = obstacleGrid.size
        val n = obstacleGrid[0].size
        val array = IntArray(n)

        array[0] = if (obstacleGrid[0][0] == 1) 0 else 1
        for (i in 0 until m) {
            for (j in 0 until n) {
                if (obstacleGrid[i][j] == 1) {
                    array[j] = 0
                    continue
                }
                if (j >= 1 && obstacleGrid[i][j - 1] == 0) {
                    array[j] += array[j - 1]
                }
            }
        }

        return array.last()
    }

    fun rob(nums: IntArray): Int {
        //f(i) = max(f(i-1),f(i-2)+nums[i])
        if (nums.isEmpty()) return 0
        val check = check(nums, 0, nums.lastIndex - 1)
        val check1 = check(nums, 1, nums.lastIndex)
        return Math.max(check, check1)
    }


    fun check(nums: IntArray, start: Int, end: Int): Int {
        var pre = 0
        var now = 0
        for (i in start..end) {
            val temp = now
            now = Math.max(now, pre + nums[i])
            pre = temp
        }
        return now
    }

    fun plusOne(digits: IntArray): IntArray {
        for (i in digits.lastIndex downTo 0) {
            digits[i]++
            if (digits[i] % 10 == 0) {
                digits[i] = 0
            } else {
                return digits
            }
        }

        val intArray = IntArray(digits.size + 1)
        intArray[0] = 1
        return intArray
    }

    fun trap(height: IntArray): Int {
        var sum = 0
        val maxLeft = IntArray(height.size)
        val maxRight = IntArray(height.size)
        for (i in 1..height.lastIndex) {
            maxLeft[i] = Math.max(maxLeft[i - 1], height[i - 1])
        }

        for (i in height.lastIndex - 1 downTo 0) {
            maxRight[i] = Math.max(maxRight[i + 1], height[i + 1])
        }

        height.forEachIndexed { index, value ->
            val min = Math.min(maxLeft[index], maxRight[index])
            if (min > value) {
                sum += (min - value)
            }
        }
        return sum
    }

    fun generateParenthesis(n: Int): List<String> {
        val list = arrayListOf<String>()
        dfs("", list, n, n)
        return list
    }

    fun dfs(curString: String, list: ArrayList<String>, left: Int, right: Int) {
        if (left == 0 && right == 0) {
            list.add(curString)
            return
        }
        if (left > right) {
            return
        }
        if (left > 0) {
            dfs("$curString(", list, left - 1, right)
        }
        if (right > 0) {
            dfs("$curString)", list, left, right - 1)
        }
    }

    fun invertTree(root: TreeNode?): TreeNode? {

        //终止条件
        if (root == null) {
            return null
        }

        //处理当前层

        //递归
        val left = invertTree(root.left)
        val right = invertTree(root.right)
        root.left = right
        root.right = left
        //还原
        return root
    }

    private var cur = Long.MIN_VALUE
    fun isValidBST(root: TreeNode?): Boolean {
        if (root == null) return true
        if (!isValidBST(root.left)) {
            return false
        }
        if (root.`val` <= cur) {
            return false
        }
        cur = root.`val`.toLong()
        //中序遍历
        return isValidBST(root.right)
    }


    fun bfs(root: TreeNode?, left: TreeNode?, right: TreeNode?): Boolean {
        if (root == null) {
            return true
        }
        if (left != null && root.`val` <= left.`val`) {
            return false
        }
        if (right != null && root.`val` >= right.`val`) {
            return false
        }
        if (!bfs(root, root.left, root.right)) {
            return false
        }
        if (!bfs(root, root.left, root.right)) {
            return false
        }
        return true
    }

    fun wordPattern(pattern: String, s: String): Boolean {
        val split = s.split(" ")
        val map = hashMapOf<Char, String>()
        if (pattern.length != split.size) return false
        pattern.forEachIndexed { index, c ->
            if (map.containsKey(c)) {
                if (split[index] != map[c]) return false
            } else {
                if (map.containsValue(split[index])) return false
                map[c] = split[index]
            }
        }
        return true
    }

    private var memo: Array<Array<Int?>>? = null

    fun minimumTotal(triangle: List<List<Int>>): Int {
//        memo = Array(triangle.size) { Array<Int?>(triangle.size){null} }
//        return search(triangle, 0, 0)

        val size = triangle.size
        val memo = IntArray(size + 1)
        for (i in size - 1 downTo 0) {
            for (j in 0..i) {
                memo[j] = Math.min(memo[j], memo[j + 1]) + triangle[i][j]
            }
        }
        return memo[0]
    }

    fun longestCommonSubsequence(text1: String, text2: String): Int {
        val m = text1.length
        val n = text2.length
        val memo = Array(m + 1) { IntArray(n + 1) }
        for (i in 1..m) {
            for (j in 1..n) {
                if (text1[i - 1] == text2[j - 1]) {
                    memo[i][j] = memo[i - 1][j - 1] + 1
                } else {
                    memo[i][j] = Math.max(memo[i - 1][j], memo[i][j - 1])
                }
            }
        }
        return memo[m][n]
    }

    fun search(triangle: List<List<Int>>, i: Int, j: Int): Int {
        if (triangle.size == i) {
            return 0
        }
        memo?.get(i)?.get(j)?.let {
            return it
        }
        val sum = triangle[i][j] + min(search(triangle, i + 1, j), search(triangle, i + 1, j + 1))
        memo!![i][j] = sum
        return sum
    }

    private var result = -1
    fun coinChange(coins: IntArray, amount: Int): Int {
        val memo = IntArray(amount + 1)
        for (i in 1..amount) {
            var min = Int.MAX_VALUE
            coins.forEach {
                if (i - it >= 0 && memo[i - it] < min) {
                    min = memo[i - it] + 1
                }
            }
            memo[i] = min
        }
        return if (memo[amount] == Int.MAX_VALUE) -1 else memo[amount]
    }

    fun maxProfit(prices: IntArray): Int {
        var minPrice = Int.MAX_VALUE
        var maxSpread = 0
        prices.forEach {
            if (it < minPrice) {
                minPrice = it
            } else if (it - minPrice > maxSpread) {
                maxSpread = it - minPrice
            }
        }
        return maxSpread
    }

    fun dfs(coins: IntArray, amount: Int, memo: IntArray): Int {
        if (amount < 0) {
            return -1
        }
        if (amount == 0) {
            return 0
        }
        if (memo[amount - 1] != 0) {
            return memo[amount - 1]
        }
        var min = Int.MAX_VALUE
        coins.forEach {
            val dfs = dfs(coins, amount - it, memo)
            if (dfs in 0 until min) {
                min = dfs + 1; // 加1，是为了加上得到res结果的那个步骤中，兑换的一个硬币
            }
        }
        memo[amount - 1] = if (min == Int.MAX_VALUE) {
            -1
        } else {
            min
        }
        return memo[amount - 1]
    }

//
//    fun quickSort(array: IntArray, begin: Int, end: Int) {
////        if (end <= begin) return
////        val index = partition(array, begin, end)
////        quickSort(array, begin, index - 1)
////        quickSort(array, index + 1, end)
//
//        if (end <= begin) return
//        val index = partition(array, begin, end)
//        quickSort(array, begin, index - 1)
//        quickSort(array, index + 1, end)
//    }
//
//    private fun partition(array: IntArray, begin: Int, end: Int): Int {
////        val temp = array[begin]
////        var low = begin
////        var high = end
////        while (low < high) {
////            while (low < high && array[high] >= temp) {
////                high--
////            }
////            array[low] = array[high]
////            while (low < high && array[low] <= temp) {
////                low++
////            }
////            array[high] = array[low]
////        }
////        array[low] = temp
////        return low
//        val temp = array[begin]
//        var low = begin
//        var high = end
//        while (low < high) {
//            while (low < high && array[high] >= temp) high--
//            array[low] = array[high]
//            while (low < high && array[low] <= temp) low++
//            array[high] = array[low]
//        }
//        array[low] = temp
//        return low
//    }

    fun sortArray(nums: IntArray): IntArray {
        quickSort(nums, 0, nums.lastIndex)
        return nums
    }

    fun quickSort(arr: IntArray, start: Int, end: Int) {
        if (start < end) {
            val index = part(arr, start, end)
            quickSort(arr, start, index - 1)
            quickSort(arr, index + 1, end)
        }
    }

    fun part(arr: IntArray, start: Int, end: Int): Int {
//        val temp = arr[start]
//        var r = start
//        var l = end
//        while (r < l) {
//            while (r < l && arr[l] >= temp) l--
//            arr[r] = arr[l]
//            while (r < l && arr[r] <= temp) r++
//            arr[l] = arr[r]
//        }
//        arr[r] = temp
//        return l

        var p = start+1
        for (i in p..end){
            if (arr[i]<arr[start]){
                arr[i]=arr[p].also { arr[p] = arr[i] }
                p++
            }
        }
        arr[start]=arr[p-1].also { arr[p-1] = arr[start] }
        return  p-1

//        var p = start + 1
//
//        for (i in p..end) {
//            if (arr[i] < arr[start]) {
//                arr[i] = arr[p].also { arr[p] = arr[i] }
//                p++
//            }
//
//        }
//        arr[start] = arr[p - 1].also { arr[p - 1] = arr[start] }
//        return p - 1
    }


    fun quickSort(array: IntArray) {
        for (i in 0 until array.lastIndex) {
            for (j in 0 until array.lastIndex - i) {
                if (array[j] > array[j + 1]) array[j] = array[j + 1].also { array[j + 1] = array[j] }
            }
        }
    }


    class ListNode(var `val`: Int) {
        var next: ListNode? = null
    }

    companion object {
        var mHashMap: HashMap<String, String>? = null

        val hashMap: HashMap<String, String>? by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            mHashMap
        }

        var content: String? = null
    }
}
