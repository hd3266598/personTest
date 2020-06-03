package com.test.persontest

import java.util.HashMap

class Solutions {

    fun lengthOfLongestSubstring(s: String): Int {
        val n = s.length
        var ans = 0
        val map = HashMap<Char, Int>()
        var end = 0
        var start = 0
        while (end < n) {
            val alpha = s[end]
            if (map.containsKey(alpha)) {
                start = (map[alpha]!!).coerceAtLeast(start)
            }
            ans = ans.coerceAtLeast(end - start + 1)
            map[s[end]] = end + 1
            end++
        }
        return ans
    }
}
