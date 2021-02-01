/**
 * FileName: BaseApplication
 * Author: huangda
 * Date: 2021/2/1 15:17
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
package com.test.persontest.activity

import android.app.Application
import android.util.Log

/**
 * @ClassName: BaseApplication
 * @Description: java类作用描述
 * @Author: huangda
 * @Date: 2021/2/1 15:17
 */
class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Log.i("BaseApplication", "onCreate:BaseApplication")
    }
}