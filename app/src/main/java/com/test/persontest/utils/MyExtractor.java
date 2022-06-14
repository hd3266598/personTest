package com.test.persontest.utils;

import android.media.MediaExtractor;
import android.media.MediaFormat;

import java.io.IOException;
import java.nio.ByteBuffer;

public class MyExtractor {
    private MediaExtractor mediaExtractor;
    private int videoTrackId;
    private int audioTrackId;
    private MediaFormat videoFormat;
    private MediaFormat audioFormat;
    private long curSampleTime;
    private int curSampleFlags;

    public MyExtractor(String path) {
        try {
            mediaExtractor = new MediaExtractor();
            // 设置数据源
            mediaExtractor.setDataSource(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //拿到所有的轨道
        int count = mediaExtractor.getTrackCount();
        for (int i = 0; i < count; i++) {
            //根据下标拿到 MediaFormat
            MediaFormat format = mediaExtractor.getTrackFormat(i);
            //拿到 mime 类型
            String mime = format.getString(MediaFormat.KEY_MIME);
            //拿到视频轨
            if (mime.startsWith("video")) {
                videoTrackId = i;
                videoFormat = format;
            } else if (mime.startsWith("audio")) {
                //拿到音频轨
                audioTrackId = i;
                audioFormat = format;
            }

        }
    }

    /**
     * 获取音频 MediaFormat
     *
     * @return
     */
    public MediaFormat getAudioFormat() {
        return audioFormat;
    }

    /**
     * 获取视频 MediaFormat
     *
     * @return
     */
    public MediaFormat getVideoFormat() {
        return videoFormat;
    }

    /**
     * 获取当前帧的标志位
     *
     * @return
     */
    public int getCurSampleFlags() {
        return curSampleFlags;
    }

    /**
     * 获取当前帧的时间戳
     *
     * @return
     */
    public long getCurSampleTime() {
        return curSampleTime;
    }

    /**
     * 释放资源
     */
    public void release() {
        mediaExtractor.release();
    }

    /**
     * 读取一帧的数据
     *
     * @param buffer
     * @return #MyExtractor#readBuffer
     */
   public int readBuffer(ByteBuffer buffer, boolean video) {
        //先清空数据
        buffer.clear();
        //选择要解析的轨道
        mediaExtractor.selectTrack(video ? videoTrackId : audioTrackId);
        //读取当前帧的数据
        int bufferCount = mediaExtractor.readSampleData(buffer, 0);
        if (bufferCount < 0) {
            return -1;
        }
        //记录当前时间戳
        curSampleTime = mediaExtractor.getSampleTime();
        //记录当前帧的标志位
        curSampleFlags = mediaExtractor.getSampleFlags();
        //进入下一帧
        mediaExtractor.advance();
        return bufferCount;
    }
}