/**
 * FileName: OneGlSurfaceView
 * Author: huangda
 * Date: 2021/1/18 15:27
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
package com.test.persontest.widget

import android.content.Context
import android.opengl.GLES32
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.os.SystemClock
import android.util.AttributeSet
import com.test.persontest.widget.graphics.Square
import com.test.persontest.widget.graphics.Triangle
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


/**
 * @ClassName: OneGlSurfaceView
 * @Description: java类作用描述
 * @Author: huangda
 * @Date: 2021/1/18 15:27
 */
class OneGlSurfaceView(context: Context, attributeSet: AttributeSet) : GLSurfaceView(context, attributeSet) {
    init {
        //设置gl版本
        setEGLContextClientVersion(3)
        //设置渲染器
        setRenderer(OneGlRenderer())
    }

    //渲染器
    inner class OneGlRenderer : Renderer {
        private var mSquare: Square? = null
        private var mTriangle: Triangle? = null

        private val mMVPMatrix = FloatArray(16)
        private val mProjectionMatrix = FloatArray(16)
        private val mViewMatrix = FloatArray(16)

        private val mRotationMatrix = FloatArray(16)

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            GLES32.glClearColor(0f, 0f, 0f, 1f)
            // 初始化triangle
            // 初始化triangle
            mTriangle = Triangle()
            // 初始化 square
            // 初始化 square
            mSquare = Square()
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            GLES32.glViewport(0, 0, width, height)

            val ratio = width.toFloat() / height

            // 这个投影矩阵被应用于对象坐标在onDrawFrame（）方法中
            Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 7f)
        }

        override fun onDrawFrame(gl: GL10?) {
            val scratch = FloatArray(16)
            // Set the camera position (View matrix)
            Matrix.setLookAtM(mViewMatrix, 0, 0f, 0f, -3f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

            // Calculate the projection and view transformation
            Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

            // 创建一个旋转矩阵
            val time = SystemClock.uptimeMillis() % 4000L
            val angle = 0.090f * time.toInt()
            Matrix.setRotateM(mRotationMatrix, 0, angle, 0f, 0f, -1.0f)

            // 将旋转矩阵与投影和相机视图组合在一起
            // Note that the mMVPMatrix factor *must be first* in order
            // for the matrix multiplication product to be correct.
            Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0)

            // Draw triangle
            mTriangle?.draw(scratch)
        }
    }
}