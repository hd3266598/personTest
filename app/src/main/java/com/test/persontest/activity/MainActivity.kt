package com.test.persontest.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.MessageQueue
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.test.persontest.R
import com.test.persontest.activity.appbrand.ui.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i("TAG", "onCreate: ${this.javaClass.simpleName}")

        btn_web_router.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            startActivity(intent)
        }
        btn_router.setOnClickListener {
            val intent = Intent(this, AppBrandUI::class.java)
            startActivity(intent)
        }
        btn_router1.setOnClickListener {
            val intent = Intent(this, AppBrandUI1::class.java)
            startActivity(intent)
        }
        btn_router2.setOnClickListener {
            val intent = Intent(this, AppBrandUI2::class.java)
            startActivity(intent)
        }
        btn_router3.setOnClickListener {
            val intent = Intent(this, AppBrandUI3::class.java)
            startActivity(intent)
        }
        btn_router4.setOnClickListener {
            val intent = Intent(this, AppBrandUI4::class.java)
            startActivity(intent)
        }
        btn_router5.setOnClickListener {
            val intent = Intent(this, AppBrandUI5::class.java)
            startActivity(intent)
        }

        search(::find)

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA), WebViewActivity.PERMISSION_REQUEST_CODE)
    }


    fun Context.search(l: (String) -> (Boolean) -> Unit) {
        l("ee")
    }


    fun find(s: String): (Boolean) -> Unit {
        return ::realFind
    }


    fun realFind(b: Boolean) {

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
