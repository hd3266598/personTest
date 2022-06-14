package com.test.persontest.activity.appbrand.ui

import android.os.Bundle
import android.util.Log
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.test.persontest.R
import com.test.persontest.model.Data
import com.test.persontest.model.VideoBean
import kotlinx.android.synthetic.main.activity_web_view_layout.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.*
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.io.IOException


class WebViewActivity : AppCompatActivity(), CoroutineScope by MainScope(), Callback {
    private val TAG = "WebViewActivity"
    private val gson = Gson()
    private var baseUrl: String = ""

    private val headers = Headers.Builder().add("Accept", "*/*").add("Accept-Language", "en-US,en;q=0.5").add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.116 Safari/537.36")

    private val okHttpClient = OkHttpClient()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view_layout)


        btn_parse.setOnClickListener {
            baseUrl = edit_url.text.toString()
            if (baseUrl.isNotEmpty()) {
                val request: Request = Request.Builder().url(baseUrl).get().headers(headers.build()).build()
                okHttpClient.newCall(request).enqueue(this)
            }
        }
    }

    private fun parseHtml(xml: String?) {
        if (xml.isNullOrEmpty()) return
        launch(Dispatchers.IO) {
            try {
                println(xml)
                Log.i("parseHtml", "parseHtml: $xml")
                val doc = Jsoup.parse(xml)
                Log.i("Jsoup", "Jsoup: $doc")
                val elements: Elements = doc.select("script")
                for (ele in elements) {
                    val text = ele.text()
                    if (text.contains("__playinfo__")) {
                        text.split("=").lastOrNull()?.let {
                            val bean = gson.fromJson(it, VideoBean::class.java).data
                            download(bean)
                        }
                        Log.i("info", "$title:$ele")
                        break
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


    private fun download(videoBean: Data, acc_quality: Int = 0) {
        val quality = videoBean.accept_description[acc_quality]
        val duration = videoBean.dash.duration
        val videoUrl = videoBean.dash.video[acc_quality].baseUrl
        val audioUrl = videoBean.dash.audio[acc_quality].baseUrl
        Log.i(TAG, "download: 当前视频清晰度为{$quality}，时长{${duration / 60}}分{${duration % 60}}秒")
        downloadSingle(videoUrl, audioUrl)
    }


    private fun downloadSingle(videoUrl: String, audioUrl: String) {
        headers.add("Referer", baseUrl)
        Log.i(TAG, "视频下载开始：%s")
        //下载并保存视频
        val request: Request = Request.Builder().url(videoUrl).get().headers(headers.build()).build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val size = (response.headers["content-length"]?.toInt() ?: 0) / 1024 / 1024
                Log.i(TAG, "视频大小：${"%.2f".format(size)}MB")
            }

        })
    }

    class InJavaScriptLocalObj {
        @JavascriptInterface
        fun showSource(html: String) {
            //html 就是网页的数据 </span>
            println("====>html=$html")
        }
    }

    override fun onFailure(call: Call, e: IOException) {
        Log.e("onFailure", "onFailure: ${e.message}")
    }

    override fun onResponse(call: Call, response: Response) {
        if (response.isSuccessful) {
            parseHtml(response.body?.string())
        }
    }
}