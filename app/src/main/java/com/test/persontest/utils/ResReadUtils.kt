package com.test.persontest.utils

import android.content.Context
import android.content.res.Resources
import okio.Okio
import okio.buffer
import okio.source
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception

object ResReadUtils {
    /**
     * 读取资源
     * @param resourceId
     */
    fun readResource(context: Context, resourceId: Int): String {
        try {
            val inputStream: InputStream = context.resources.openRawResource(resourceId)
            return inputStream.source().buffer().readUtf8()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}