package com.test.persontest.utils;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class LocalFileUtils {
    public static void videoSaveToNotifyGalleryToRefreshWhenVersionGreaterQ(Context context, File destFile) {
        ContentValues values = new ContentValues();
        Uri uriSavedVideo;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/Folder");
            values.put(MediaStore.Video.Media.TITLE, destFile.getName());
            values.put(MediaStore.Video.Media.DISPLAY_NAME, destFile.getName());
            values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
            values.put(MediaStore.Video.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
            values.put(MediaStore.Video.Media.DATA, destFile.getAbsolutePath());

            Uri collection = MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
            uriSavedVideo = context.getContentResolver().insert(collection, values);

        } else {
            values.put(MediaStore.Video.Media.TITLE, destFile.getName());
            values.put(MediaStore.Video.Media.DISPLAY_NAME, destFile.getName());
            values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
            values.put(MediaStore.Video.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
            values.put(MediaStore.Video.Media.DATA, destFile.getAbsolutePath());

            uriSavedVideo = context.getContentResolver().
                    insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Video.Media.DATE_TAKEN, System.currentTimeMillis());
            values.put(MediaStore.Video.Media.IS_PENDING, 1);
        }

        ParcelFileDescriptor pfd;
        try {
            pfd = context.getContentResolver().openFileDescriptor(uriSavedVideo, "w");
            FileOutputStream out = new FileOutputStream(pfd.getFileDescriptor());

            FileInputStream in = new FileInputStream(destFile);

            byte[] buf = new byte[8192];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            out.close();
            in.close();
            pfd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.clear();
            values.put(MediaStore.Video.Media.IS_PENDING, 0);
            context.getContentResolver().update(uriSavedVideo, values, null, null);
        }

        Log.i("LocalFileUtils", "videoSaveToNotifyGalleryToRefreshWhenVersionGreaterQ: " + "编译结束");
    }
}
