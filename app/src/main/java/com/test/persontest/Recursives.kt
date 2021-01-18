/**
 * FileName: Recursives
 * Author: huangda
 * Date: 2020/7/15 13:26
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
package com.test.persontest

/**
 * @ClassName: Recursives
 * @Description: java类作用描述
 * @Author: huangda
 * @Date: 2020/7/15 13:26
 */
class Recursives {
    //输入：["H","a","n","n","a","h"]
    //输出：["h","a","n","n","a","H"]
    fun reverseString(s: CharArray): Unit {
        reverse(0, s)
        println(s)
    }

    fun reverse(index: Int, charArray: CharArray) {
        if (index == charArray.size / 2) {
            return
        }
        charArray[charArray.lastIndex - index] =
            charArray[index].also { charArray[index] = charArray[charArray.lastIndex - index] }
        reverse(index + 1, charArray)
    }


    /**
     * Example:
     * var li = ListNode(5)
     * var v = li.`val`
     * Definition for singly-linked list.
     * class ListNode(var `val`: Int) {
     *     var next: ListNode? = null
     * }
     */


    fun swapPairs(head: ListNode?): ListNode? {
        if (head?.next == null) {
            return head
        }
        val head1 = head.next
        val head2 = swapPairs(head.next?.next)
        head1?.next = head
        head.next = head2
        return head1
    }

    class ListNode(var `val`: Int) {
        var next: ListNode? = null
    }
}