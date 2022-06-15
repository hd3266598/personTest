package com.test.persontest.utils

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.os.ParcelFileDescriptor
import android.util.Log
import okio.buffer
import okio.sink
import okio.source
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Exception

object LocalFileUtils {
    fun videoSaveToNotifyGalleryToRefreshWhenVersionGreaterQ(context: Context, destFile: File) {
        val values = ContentValues()
        val uriSavedVideo: Uri? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/Folder")
            values.put(MediaStore.Video.Media.TITLE, destFile.name)
            values.put(MediaStore.Video.Media.DISPLAY_NAME, destFile.name)
            values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
            values.put(MediaStore.Video.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
            values.put(MediaStore.Video.Media.DATA, destFile.absolutePath)
            val collection = MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            context.contentResolver.insert(collection, values)
        } else {
            values.put(MediaStore.Video.Media.TITLE, destFile.name)
            values.put(MediaStore.Video.Media.DISPLAY_NAME, destFile.name)
            values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
            values.put(MediaStore.Video.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
            values.put(MediaStore.Video.Media.DATA, destFile.absolutePath)
            context.contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Video.Media.DATE_TAKEN, System.currentTimeMillis())
            values.put(MediaStore.Video.Media.IS_PENDING, 1)

            val pfd: ParcelFileDescriptor?
            try {
                uriSavedVideo?.let {
                    pfd = context.contentResolver.openFileDescriptor(uriSavedVideo, "w")
                    pfd?.let {
                        val out = FileOutputStream(pfd.fileDescriptor)
                        destFile.source().buffer().use { _source ->
                            out.sink().buffer().use {
                                it.writeAll(_source)
                            }
                        }
                    }
                    values.clear()
                    values.put(MediaStore.Video.Media.IS_PENDING, 0)
                    context.contentResolver.update(uriSavedVideo, values, null, null)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        Log.i("LocalFileUtils", "videoSaveToNotifyGalleryToRefreshWhenVersionGreaterQ: " + "通知相册更新")
    }
}