package com.test.persontest.activity.appbrand.ui

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.media.MediaCodec
import android.media.MediaFormat
import android.media.MediaMuxer
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.gson.Gson
import com.test.persontest.R
import com.test.persontest.model.LocalVideo
import com.test.persontest.model.video.Data
import com.test.persontest.model.video.VideoBean
import com.test.persontest.utils.LocalFileUtils
import com.test.persontest.utils.MyExtractor
import com.test.persontest.widget.LoadingDialog
import kotlinx.android.synthetic.main.activity_web_view_layout.*
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okio.buffer
import okio.sink
import okio.source
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.io.File
import java.io.IOException
import java.nio.ByteBuffer
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


class WebViewActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    private val TAG = "Video"
    private val gson = Gson()
    private var baseUrl: String = ""

    private val okHttpClient = getUnsafeOkHttpClient()

    private val data = arrayListOf<LocalVideo>()

    private val adapter = Adapter()

    private var dialog: LoadingDialog? = null

    private var path: String? = null

    private val simpleDateFormat = SimpleDateFormat("HH:mm:ss", Locale.CHINA)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view_layout)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter


        btn_parse.setOnClickListener {
            baseUrl = edit_url.text.toString().trim()
            if (baseUrl.isNotEmpty()) {
                showLoadingDialog()
                dialog?.setTitle("loading")

                okHttpClient?.newCall(Request.Builder().url(baseUrl).get().build())?.enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        hideLoadingDialog()
                        Log.i(TAG, "onFailure: 连接错误")
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) {
                            baseUrl = response.request.url.toString()
                            val request: Request = Request.Builder().url(baseUrl).get().build()
                            okHttpClient.newCall(request).enqueue(object : Callback {
                                override fun onFailure(call: Call, e: IOException) {
                                    hideLoadingDialog()
                                    Log.e("onFailure", "onFailure: ${e.message}")
                                }

                                override fun onResponse(call: Call, response: Response) {
                                    if (response.isSuccessful) {
                                        parseHtml(response.body?.string())
                                    }
                                }
                            })
                        }
                    }
                })
            }
        }
    }

    override fun onResume() {
        super.onResume()
        refreshData()

        edit_url.post {
            edit_url.setText(getClipboardContent())
        }
    }

    private fun refreshData() {
        data.clear()
        val externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        externalFilesDir?.listFiles()?.onEach {
            data.add(LocalVideo(it.name.removeSuffix(".mp4"), it.absolutePath))
        }
        adapter.notifyDataSetChanged()
    }


    private fun parseHtml(xml: String?) {
        if (xml.isNullOrEmpty()) return
        launch(Dispatchers.IO) {
            try {
                Log.i("parseHtml", "parseHtml: $xml")
                val doc = Jsoup.parse(xml)
                Log.i("Jsoup", "Jsoup: $doc")
                val name = doc.title()
                val elements: Elements = doc.select("script")
                for (ele in elements) {
                    if (ele.childNodeSize() > 0) {
                        val node = ele.childNode(0)
                        val attr = node.attr("#data")
                        Log.i(TAG, "parseHtml:$attr ")
                        val tag = "window.__playinfo__="
                        if (attr.contains(tag)) {
                            val substring = attr.substring(tag.length, attr.length)
                            val bean = gson.fromJson(substring, VideoBean::class.java).data
                            download(name, bean)
                            Log.i("info", "$title:$ele")
                            break
                        }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


    private suspend fun download(name: String, videoBean: Data, acc_quality: Int = 0) {
        val quality = videoBean.accept_description[acc_quality]
        val duration = videoBean.dash.duration
        val videoUrl = videoBean.dash.video[acc_quality].baseUrl
        val audioUrl = videoBean.dash.audio[acc_quality].baseUrl
        Log.i(TAG, "download: 当前视频清晰度为{$quality}，时长{${duration / 60}}分{${duration % 60}}秒")
        downloadSingle(name, videoUrl, audioUrl)
    }


    private suspend fun downloadSingle(name: String, videoUrl: String, audioUrl: String) = withContext(Dispatchers.IO) {
        val headers = Headers.Builder()
        headers.add("Referer", baseUrl)
        headers.add("Range", "bytes=0-")
        Log.i(TAG, "视频下载开始：$name")
        //下载并保存视频
        okHttpClient?.newCall(Request.Builder().url(videoUrl).head().headers(headers.build()).build())?.execute()?.use {
            val size = (it.headers["content-length"]?.toDouble() ?: 0.0) / 1024.0 / 1024.0
            Log.i(TAG, "视频大小：${"%.2f".format(size)}MB")
            withContext(Dispatchers.Main) {
                dialog?.setTitle("视频：${"%.2f".format(size)}MB")
            }
        }

        val video: Request = Request.Builder().url(videoUrl).get().headers(headers.build()).build()
        val videoPath = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.absolutePath + File.separator + "${name}_video.mp4"
        try {
            val file = File(videoPath)
            if (file.exists()) {
                file.delete()
            }
            if (file.createNewFile()) {
                okHttpClient?.newCall(video)?.execute()?.body?.byteStream()?.source()?.buffer()?.use { source ->
                    file.sink().use {
                        it.buffer().writeAll(source)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                hideLoadingDialog()
            }
        }


        //下载并保存视频
        okHttpClient?.newCall(Request.Builder().url(audioUrl).head().headers(headers.build()).build())?.execute()?.use {
            val size = (it.headers["content-length"]?.toDouble() ?: 0.0) / 1024.0 / 1024.0
            Log.i(TAG, "音频大小：${"%.2f".format(size)}MB")
            withContext(Dispatchers.Main) {
                dialog?.setTitle("音频：${"%.2f".format(size)}MB")
            }
        }

        val audio: Request = Request.Builder().url(audioUrl).get().headers(headers.build()).build()
        val audioPath = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.absolutePath + File.separator + "${name}_audio.mp3"
        try {
            val file = File(audioPath)
            if (file.exists()) {
                file.delete()
            }
            if (file.createNewFile()) {
                okHttpClient?.newCall(audio)?.execute()?.body?.byteStream()?.source()?.buffer()?.use { source ->
                    file.sink().use {
                        it.buffer().writeAll(source)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                hideLoadingDialog()
            }
        }

        Log.i(TAG, "downloadSingle: 视频下载结束")

        Log.i(TAG, "视频合成开始：$name")

        withContext(Dispatchers.Main) {
            dialog?.setTitle("合成")
        }

        path = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.absolutePath + File.separator + "${name}.mp4"
        var file = File(path)
        if (file.exists()) {
            path = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.absolutePath + File.separator + "${name}-${simpleDateFormat.format(Date())}.mp4"
            file = File(path)
        }
        if (file.createNewFile()) {
            mixerVoice2Video(videoPath, audioPath, path)
        }

        Log.i(TAG, "视频合成结束：$name||$path")
        //删除源文件
        File(audioPath).delete()
        File(videoPath).delete()

        withContext(Dispatchers.Main) {
            Toast.makeText(this@WebViewActivity, "下载成功", Toast.LENGTH_SHORT).show()
            hideLoadingDialog()
            refreshData()
        }
    }


    private fun getUnsafeOkHttpClient(): OkHttpClient? {
        return try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts: Array<TrustManager> = arrayOf<TrustManager>(
                @SuppressLint("CustomX509TrustManager")
                object : X509TrustManager {
                    override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {
                    }

                    override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
            )

            // Install the all-trusting trust manager
            val sslContext: SSLContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { p0, p1 -> true }
//            val interceptor = HttpLoggingInterceptor()
//            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//            builder.addInterceptor(interceptor)
            builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @SuppressLint("WrongConstant")
    @Throws(java.lang.Exception::class)
    fun mixerVoice2Video(videoPath: String?, audioPath: String?, savePath: String?) {
        try {
            //创建音频的 MediaExtractor
            val audioExtractor = MyExtractor(audioPath)
            //创建视频的 MediaExtractor
            val videoExtractor = MyExtractor(videoPath)
            //拿到音频的 mediaFormat
            val audioFormat: MediaFormat = audioExtractor.audioFormat
            //拿到音频的 mediaFormat
            val videoFormat: MediaFormat = videoExtractor.videoFormat
            val mediaMuxer = MediaMuxer(savePath!!, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)
            //添加音频
            val audioId = mediaMuxer.addTrack(audioFormat)
            //添加视频
            val videoId = mediaMuxer.addTrack(videoFormat)
            //开始混合，等待写入
            mediaMuxer.start()
            val buffer: ByteBuffer = ByteBuffer.allocate(500 * 1024)
            val info = MediaCodec.BufferInfo()

            //混合视频
            var videoSize: Int
            //读取视频帧的数据，直到结束
            while (videoExtractor.readBuffer(buffer, true).also { videoSize = it } > 0) {
                //从0帧开始读取
                info.offset = 0
                //本次读取的长度
                info.size = videoSize
                info.presentationTimeUs = videoExtractor.curSampleTime
                info.flags = videoExtractor.curSampleFlags
                mediaMuxer.writeSampleData(videoId, buffer, info)
            }
            //写完视频，再把音频混合进去
            var audioSize: Int
            //读取音频帧的数据，直到结束
            while (audioExtractor.readBuffer(buffer, false).also { audioSize = it } > 0) {
                info.offset = 0
                info.size = audioSize
                info.presentationTimeUs = audioExtractor.curSampleTime
                info.flags = audioExtractor.curSampleFlags
                mediaMuxer.writeSampleData(audioId, buffer, info)
            }
            //释放资源
            audioExtractor.release()
            videoExtractor.release()
            mediaMuxer.stop()
            mediaMuxer.release()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun showLoadingDialog() {
        try {
            if (dialog == null) {
                dialog = LoadingDialog(this)
            }
            if (!dialog!!.isShowing) {
                dialog?.show()
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun hideLoadingDialog() {
        if (dialog != null) {
            dialog?.dismiss()
            dialog = null
        }
    }

    inner class Adapter : BaseQuickAdapter<LocalVideo, BaseViewHolder>(R.layout.item_local_video_layout, data = data) {
        override fun convert(holder: BaseViewHolder, item: LocalVideo) {
            val imageView = holder.getView<ImageView>(R.id.img_video)
            item.path?.let { _path ->
                imageView.load(File(_path))
                holder.getView<Button>(R.id.btn_play).setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    val data: Uri = Uri.parse(_path)
                    intent.setDataAndType(data, "video/mp4")
                    startActivity(intent)
                }
                holder.getView<Button>(R.id.btn_clip).setOnClickListener {
                    try {
                        //通知相册
                        path?.let {
                            launch {
                                showLoadingDialog()
                                LocalFileUtils.videoSaveToNotifyGalleryToRefreshWhenVersionGreaterQ(this@WebViewActivity, File(it))
                                hideLoadingDialog()
                                Toast.makeText(this@WebViewActivity, "已同步", Toast.LENGTH_SHORT).show()

                                val pickIntent = Intent(
                                    Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                )
                                pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "video/*")
                                startActivity(pickIntent)
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }


                holder.getView<Button>(R.id.btn_delete).setOnClickListener {
                    File(_path).delete()
                    refreshData()
                    Toast.makeText(this@WebViewActivity, "已删除", Toast.LENGTH_SHORT).show()
                }
            }
            holder.getView<TextView>(R.id.tv_title).text = item.title


        }
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 1001
    }

    /**
     * 获取剪切板上的内容
     */
    @Nullable
    fun getClipboardContent(): String? {
        val cm: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val data: ClipData? = cm.primaryClip
        if (data != null && data.itemCount > 0) {
            val item = data.getItemAt(0)
            if (item != null) {
                val sequence = item.coerceToText(this)
                if (sequence != null) {
                    val regex = "https?://(?:[-\\w.]|%[\\da-fA-F]{2})+[^\\u4e00-\\u9fa5]+[\\w-_/?&=#%:]{0}"
                    val matcher = Pattern.compile(regex).matcher(sequence)
                    if (matcher.find()) return matcher.group()
                    return sequence.toString()
                }
            }
        }
        return null
    }
}