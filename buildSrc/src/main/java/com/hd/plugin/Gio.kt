package com.hd.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.AppExtension
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import com.hd.plugin.lifecycle.LifecycleClassVisitor
import groovyjarjarasm.asm.ClassReader
import groovyjarjarasm.asm.ClassWriter
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.IOUtils
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

class Gio : Transform(), Plugin<Project> {
    override fun apply(target: Project) {
        val byType = target.extensions.getByType(AppExtension::class.java)
        byType.registerTransform(this)
    }

    override fun getName(): String {
        return "Gio"
    }

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    override fun isIncremental(): Boolean {
        return false
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    override fun transform(transformInvocation: TransformInvocation?) {
        val inputs = transformInvocation?.inputs
        val provider = transformInvocation?.outputProvider
        provider?.deleteAll()
        inputs?.onEach { input ->
            input.directoryInputs.onEach { directoryInput ->
                handleDirectoryInput(directoryInput, provider)
            }
            input.jarInputs.onEach { jarInput ->
                handleJarInputs(jarInput, provider)
            }
        }
        println("--------------- Gio visit end --------------- ")
    }

    private fun handleDirectoryInput(directoryInput: DirectoryInput, transformOutputProvider: TransformOutputProvider?) {
        if (directoryInput.file.isDirectory) {
            directoryInput.file.listFiles()?.forEach { file ->
                val name = file.name
                if (checkClassFile(name)) {
                    println("----------- deal with class file <' + $name + '> -----------")
                    val classReader = ClassReader(IOUtils.toByteArray(file.inputStream()))
                    val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                    val cv = LifecycleClassVisitor(classWriter)
                    classReader.accept(cv, ClassReader.EXPAND_FRAMES)
                    val code = classWriter.toByteArray()
                    val fos = FileOutputStream(file.parentFile?.absolutePath + File.separator + name)
                    fos.write(code)
                    fos.close()
                }
            }
        }
        //处理完输入文件之后，要把输出给下一个任务
        val dest = transformOutputProvider?.getContentLocation(
            directoryInput.name,
            directoryInput.contentTypes, directoryInput.scopes,
            Format.DIRECTORY
        )
        FileUtils.copyDirectory(directoryInput.file, dest)
    }

    private fun handleJarInputs(jarInput: JarInput, transformOutputProvider: TransformOutputProvider?) {
        if (jarInput.file.absolutePath.endsWith(".jar")) {
            //重名名输出文件,因为可能同名,会覆盖
            var jarName = jarInput.name
            val md5Name = DigestUtils.md5Hex(jarInput.file.absolutePath)
            if (jarName.endsWith(".jar")) {
                jarName = jarName.substring(0, jarName.length - 4)
            }
            val jarFile = JarFile(jarInput.file)
            val enumeration = jarFile.entries()
            val tmpFile =
                File(jarInput.file?.parent + File.separator.toString() + "classes_temp.jar")
            //避免上次的缓存被重复插入
            if (tmpFile.exists()) {
                tmpFile.delete()
            }
            val jarOutputStream = JarOutputStream(FileOutputStream(tmpFile))
            //用于保存
            while (enumeration.hasMoreElements()) {
                val jarEntry = enumeration.nextElement() as JarEntry
                val entryName: String = jarEntry.name
                val zipEntry = ZipEntry(entryName)
                val inputStream: InputStream = jarFile.getInputStream(jarEntry)
                //插桩class
                if (checkClassFile(entryName)) {
                    //class文件处理

                    jarOutputStream.putNextEntry(zipEntry)
                    val classReader = ClassReader(IOUtils.toByteArray(inputStream))
                    val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                    val cv = LifecycleClassVisitor(classWriter)
                    classReader.accept(cv, ClassReader.EXPAND_FRAMES)
                    val code: ByteArray = classWriter.toByteArray()
                    jarOutputStream.write(code)
                } else {
                    jarOutputStream.putNextEntry(zipEntry)
                    jarOutputStream.write(IOUtils.toByteArray(inputStream))
                }
                jarOutputStream.closeEntry()
            }
            //结束
            jarOutputStream.close()
            jarFile.close()
            val dest = transformOutputProvider?.getContentLocation(
                jarName + md5Name,
                jarInput.contentTypes, jarInput.scopes, Format.JAR
            )
            FileUtils.copyFile(tmpFile, dest)
            tmpFile.delete()
        }
    }

    /**
     * 检查class文件是否需要处理
     * @param fileName
     * @return
     */
    private fun checkClassFile(name: String): Boolean {
        //只处理需要的class文件
        return (name.endsWith(".class") && !name.startsWith("R\$")
                && "R.class" != name && "BuildConfig.class" != name
                && "android/app/Activity.class" == name)
    }
}