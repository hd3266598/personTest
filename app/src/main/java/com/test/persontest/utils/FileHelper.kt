/**
 * FileName: FileHelper
 * Author: huangda
 * Date: 2022/6/14 17:58
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
package com.test.persontest.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import java.io.*


/**
 * @ClassName: FileHelper
 * @Description: java类作用描述
 * @Author: huangda
 * @Date: 2022/6/14 17:58
 */
object FileHelper {
    fun getSDcardDCIMFile(suffix: String): String {
        return Environment.getExternalStorageDirectory().absolutePath + File.separator + "DCIM" + File.separator + suffix
    }

    /**
     * 获取视频 contentValue
     * @param paramFile
     * @param paramLong
     * @return
     */
    fun getVideoContentValues(paramFile: File, paramLong: Long): ContentValues {
        val localContentValues = ContentValues()
        localContentValues.put("title", paramFile.name)
        localContentValues.put("_display_name", paramFile.name)
        localContentValues.put("mime_type", "video/mp4")
        localContentValues.put("datetaken", java.lang.Long.valueOf(paramLong))
        localContentValues.put("date_modified", java.lang.Long.valueOf(paramLong))
        localContentValues.put("date_added", java.lang.Long.valueOf(paramLong))
        localContentValues.put("_data", paramFile.absolutePath)
        localContentValues.put("_size", java.lang.Long.valueOf(paramFile.length()))
        return localContentValues
    }


    /**
     * 将视频保存到系统图库
     *
     * @param videoFile
     * @param context
     */
    fun saveVideoToSystemAlbum(videoFile: String, context: Context): Boolean {
        return try {
            val localContentResolver: ContentResolver = context.contentResolver
            val localContentValues = getVideoContentValues(File(videoFile), System.currentTimeMillis())
            val localUri: Uri? = localContentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, localContentValues)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && context.applicationInfo.targetSdkVersion >= Build.VERSION_CODES.Q) {
                // 拷贝到指定uri,如果没有这步操作，android11不会在相册显示
                try {
                    localUri?.let {
                        val out: OutputStream? = context.contentResolver.openOutputStream(localUri)
                        if (out != null) {
                            copyFile(videoFile, out)
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri))
            //将该文件扫描到相册
            //MediaScannerConnection.scanFile(context, new String[] { videoFile }, null, null);
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * 拷贝文件
     * @param oldPath
     * @param out
     * @return
     */
    fun copyFile(oldPath: String, out: OutputStream): Boolean {
        try {
            var bytesum = 0
            var byteread = 0
            val oldfile = File(oldPath)
            if (oldfile.exists()) {
                // 读入原文件
                val inStream: InputStream = FileInputStream(oldPath)
                val buffer = ByteArray(1444)
                while (inStream.read(buffer).also { byteread = it } != -1) {
                    bytesum += byteread //字节数 文件大小
                    println(bytesum)
                    out.write(buffer, 0, byteread)
                }
                inStream.close()
                out.close()
                return true
            } else {
                Log.w(TAG, String.format("文件(%s)不存在。", oldPath))
            }
        } catch (e: Exception) {
            Log.e(TAG, "复制单个文件操作出错")
            e.printStackTrace()
        }
        return false
    }


}