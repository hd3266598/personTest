package com.test.persontest

import android.app.Application
import android.os.Environment
import android.util.Log
import dalvik.system.PathClassLoader
import java.io.File
import java.lang.reflect.Field

class CarApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        launchPatch()
    }

    private fun launchPatch() {
        val path = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.absolutePath + "/patch/"
        val file = File(path)
        file.mkdirs()
        path.let {
            val dexClassLoader = PathClassLoader("${it}/hot.dex", getClassLoader())
            try {
                val myDexClazzLoader = Class.forName("dalvik.system.BaseDexClassLoader")
                val myPathListFiled: Field = myDexClazzLoader.getDeclaredField("pathList")
                myPathListFiled.setAccessible(true)
                val myPathListObject: Any = myPathListFiled.get(dexClassLoader)
                val myElementsField: Field =
                    myPathListObject.javaClass.getDeclaredField("dexElements")
                myElementsField.setAccessible(true)
                //          自己插件的  dexElements[]
                val myElements: Any = myElementsField.get(myPathListObject)

                //     第二步   找到    系统的Elements数组    dexElements
                val baseDexClazzLoader = Class.forName("dalvik.system.BaseDexClassLoader")
                val pathListFiled: Field = baseDexClazzLoader.getDeclaredField("pathList")
                pathListFiled.setAccessible(true)
                val pathListObject: Any = pathListFiled.get(getClassLoader())
                val systemElementsField: Field =
                    pathListObject.javaClass.getDeclaredField("dexElements")
                systemElementsField.setAccessible(true)
                //系统的  dexElements[]
                val systemElements: Any = systemElementsField.get(pathListObject)
                //     第三步  上面的dexElements  数组  合并成新的  dexElements     然后通过反射重新注入系统的Field （dexElements ）变量中

//       新的     Element[] 对象
//            dalvik.system.Element
                val systemLength: Int = java.lang.reflect.Array.getLength(systemElements)
                val myLength: Int = java.lang.reflect.Array.getLength(myElements)
                //            找到 Element  的Class类型   数组    每一个成员的类型
                val sigleElementClazz = systemElements.javaClass.componentType
                val newSysteLength = myLength + systemLength
                val newElementsArray: Any =
                    java.lang.reflect.Array.newInstance(sigleElementClazz, newSysteLength)
                //融合
                for (i in 0 until newSysteLength) {
//                先融合 插件的Elements
                    if (i < myLength) {
                        java.lang.reflect.Array.set(
                            newElementsArray,
                            i,
                            java.lang.reflect.Array.get(myElements, i)
                        )
                    } else {
                        val value: Any = java.lang.reflect.Array.get(systemElements, i - myLength)
                        java.lang.reflect.Array.set(newElementsArray, i, value)
                    }
                }
                val elementsField: Field = pathListObject.javaClass.getDeclaredField("dexElements")
                elementsField.setAccessible(true)
                //            将新生成的EleMents数组对象重新放到系统中去
                elementsField.set(pathListObject, newElementsArray)
                Log.i("pathListObject", "launchPatch:$pathListObject")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}