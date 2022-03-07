/**
 * FileName: Person
 * Author: huangda
 * Date: 2022/2/7 14:56
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
package com.test.persontest

/**
 * @ClassName: Person
 * @Description: java类作用描述
 * @Author: huangda
 * @Date: 2022/2/7 14:56
 */
class Person(private var msg: String) {
    init {
        println(msg)
    }

    fun action(){
        println(msg)
    }
}