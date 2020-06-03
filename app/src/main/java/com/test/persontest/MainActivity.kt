package com.test.persontest

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.contains
import androidx.fragment.app.FragmentManager
import io.flutter.facade.Flutter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val createFragment = Flutter.createFragment("default_main")


        btn_start.setOnClickListener{
//            val createView = Flutter.createView(this, lifecycle, "default_main")
//            val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
//            if (!flutterView.contains(createView)) {
//                flutterView.addView(createView, layoutParams)
//            }

            supportFragmentManager.beginTransaction().replace(R.id.flutterView,createFragment).commitAllowingStateLoss()
        }
    }
}
