/**
 * FileName: RecursiveTest
 * Author: huangda
 * Date: 2020/7/15 13:27
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
package com.test.persontest

import junit.framework.TestCase
import org.junit.Test

/**
 * @ClassName: RecursiveTest
 * @Description: java类作用描述
 * @Author: huangda
 * @Date: 2020/7/15 13:27
 */
class RecursiveTest : TestCase() {
    private var recursives: Recursives? = null

    override fun setUp() {
        super.setUp()
        recursives = Recursives()
    }

    @Test
    fun testCheck() {
        recursives?.reverseString(charArrayOf('H', 'a', 'n','c', 'n', 'a', 'h'))
    }
}