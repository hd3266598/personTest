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
import android.os.Build
import android.util.Log
import coil.ComponentRegistry
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.VideoFrameDecoder
import coil.request.CachePolicy

/**
 * @ClassName: BaseApplication
 * @Description: java类作用描述
 * @Author: huangda
 * @Date: 2021/2/1 15:17
 */
class BaseApplication : Application(), ImageLoaderFactory {
    override fun onCreate() {
        super.onCreate()
        Log.i("BaseApplication", "onCreate:BaseApplication")
    }

    override fun newImageLoader(): ImageLoader {
        val imageLoaderBuilder = ImageLoader.Builder(this).components {
            add(VideoFrameDecoder.Factory())
        }
        imageLoaderBuilder.memoryCachePolicy(CachePolicy.ENABLED) //设置内存的缓存策略

        imageLoaderBuilder.diskCachePolicy(CachePolicy.ENABLED) //设置磁盘的缓存策略

        imageLoaderBuilder.networkCachePolicy(CachePolicy.ENABLED) //设置网络的缓存策略

        return imageLoaderBuilder.build()
    }
}