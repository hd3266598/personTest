/**
 * FileName: LocalNode
 * Author: huangda
 * Date: 2020/11/16 16:24
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
package com.test.persontest

import android.util.Log

/**
 * @ClassName: LocalNode
 * @Description: java类作用描述
 * @Author: huangda
 * @Date: 2020/11/16 16:24
 */
class LocalNode : Node(0) {
    init {
        Log.i("LocalNode", ": 初始化LocalNode")
    }
}