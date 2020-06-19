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

import android.os.Handler
import android.os.Looper
import android.os.Message
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.HashMap
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

/**
 * @ClassName: Solution
 * @Description: java类作用描述
 * @Author: huangda
 * @Date: 2019/11/11 14:14
 */
class Solution : CoroutineScope by MainScope() {
    var count = 0

    fun lengthOfLongestSubstring(s: String): Int {
        var start = 0
        var end = 0
        val list = LinkedList<Char>()
        for (i in start until end) {
            if (list.contains(s[i])) {
                start = i
            } else {
                list.add(s[i])
            }
        }
        return list.size
    }


    fun isEquals() {
        var a = 10
        val b = a
        a = 11
        print("$a||$b")
    }

    fun isNull() {
        val test: String? = null
        if (test?.length == 1) {
            print("test is null:true")
        } else {
            print("test is null:false")
        }
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

    fun check2() {

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


    companion object {
        var mHashMap: HashMap<String, String>? = null

        val hashMap: HashMap<String, String>? by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            mHashMap
        }
    }
}