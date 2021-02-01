package com.test.persontest.activity.appbrand.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.test.persontest.R
import kotlinx.android.synthetic.main.activity_web_view_layout.*


class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view_layout)

        button_exit.setOnClickListener {
            onBackPressed()
        }
    }
}