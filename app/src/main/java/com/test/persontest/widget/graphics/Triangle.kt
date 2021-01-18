package com.test.persontest.widget.graphics

import android.opengl.GLES32
import com.test.persontest.utils.OneGlUtils
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer


class Triangle {
    private val vertexBuffer: FloatBuffer

    // Set color with red, green, blue and alpha (opacity) values
    var color = floatArrayOf(255f, 0f, 0f, 1.0f)

    companion object {
        // number of coordinates per vertex in this array
        const val COORDS_PER_VERTEX = 3

        var triangleCoords = floatArrayOf( // in counterclockwise order:
            0.0f, 0.5f, 0.0f,  // top
            -0.5f, -0.5f, 0.0f,  // bottom left
            0.5f, -0.5f, 0.0f // bottom right
        )

        private const val vertexShaderCode =  // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +  // the matrix must be included as a modifier of gl_Position
                    // Note that the uMVPMatrix factor *must be first* in order
                    // for the matrix multiplication product to be correct.
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}"

        private const val fragmentShaderCode = "precision mediump float;" +
                "uniform vec4 vColor;" +
                "void main() {" +
                "  gl_FragColor = vColor;" +
                "}"
    }

    private var mProgram: Int


    init {
        // 初始化ByteBuffer，长度为arr数组的长度*4，因为一个float占4个字节
        val bb = ByteBuffer.allocateDirect(triangleCoords.size * 4)
        // 数组排列用nativeOrder
        bb.order(ByteOrder.nativeOrder())
        // 从ByteBuffer创建一个浮点缓冲区
        vertexBuffer = bb.asFloatBuffer()
        // 将坐标添加到FloatBuffer
        vertexBuffer.put(triangleCoords)
        // 设置缓冲区来读取第一个坐标
        vertexBuffer.position(0)

        val vertexShader: Int = OneGlUtils.loadShader(
            GLES32.GL_VERTEX_SHADER,
            vertexShaderCode
        )
        val fragmentShader: Int = OneGlUtils.loadShader(
            GLES32.GL_FRAGMENT_SHADER,
            fragmentShaderCode
        )

        // 创建空的OpenGL ES程序
        mProgram = GLES32.glCreateProgram()

        // 添加顶点着色器到程序中

        // 添加顶点着色器到程序中
        GLES32.glAttachShader(mProgram, vertexShader)

        // 添加片段着色器到程序中

        // 添加片段着色器到程序中
        GLES32.glAttachShader(mProgram, fragmentShader)

        // 创建OpenGL ES程序可执行文件

        // 创建OpenGL ES程序可执行文件
        GLES32.glLinkProgram(mProgram)
    }

    private var mPositionHandle = 0
    private var mColorHandle = 0

    private val vertexCount: Int = triangleCoords.size / COORDS_PER_VERTEX
    private val vertexStride = COORDS_PER_VERTEX * 4 // 4 bytes per vertex


    fun draw() {
        // 将程序添加到OpenGL ES环境
        GLES32.glUseProgram(mProgram)

        // 获取顶点着色器的位置的句柄
        mPositionHandle = GLES32.glGetAttribLocation(mProgram, "vPosition")

        // 启用三角形顶点位置的句柄
        GLES32.glEnableVertexAttribArray(mPositionHandle)

        //准备三角形坐标数据
        GLES32.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES32.GL_FLOAT, false, vertexStride, vertexBuffer)

        // 获取片段着色器的颜色的句柄
        mColorHandle = GLES32.glGetUniformLocation(mProgram, "vColor")

        // 设置绘制三角形的颜色
        GLES32.glUniform4fv(mColorHandle, 1, color, 0)

        // 绘制三角形
        GLES32.glDrawArrays(GLES32.GL_TRIANGLES, 0, vertexCount)

        // 禁用顶点数组
        GLES32.glDisableVertexAttribArray(mPositionHandle)
    }

    private var mMVPMatrixHandle = 0
    fun draw(mvpMatrix: FloatArray?) {
        //
        draw()
        // 得到形状的变换矩阵的句柄
        mMVPMatrixHandle = GLES32.glGetUniformLocation(mProgram, "uMVPMatrix")

        // 将投影和视图转换传递给着色器
        GLES32.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0)

        // 画三角形
        GLES32.glDrawArrays(GLES32.GL_TRIANGLES, 0, vertexCount)

        // 禁用顶点数组
        GLES32.glDisableVertexAttribArray(mPositionHandle)
    }
}