package com.hd.plugin.lifecycle

import groovyjarjarasm.asm.ClassVisitor
import groovyjarjarasm.asm.MethodVisitor
import groovyjarjarasm.asm.Opcodes

class LifecycleClassVisitor(classVisitor: ClassVisitor?) :
    ClassVisitor(Opcodes.ASM5, classVisitor),
    Opcodes {
    private var mClassName: String? = null
    override fun visit(version: Int, access: Int, name: String, signature: String, superName: String, interfaces: Array<String>) {
        println("LifecycleClassVisitor : visit -----> started :$name")
        mClassName = name
        super.visit(version, access, name, signature, superName, interfaces)
    }

    override fun visitMethod(access: Int, name: String, descriptor: String, signature: String, exceptions: Array<String>): MethodVisitor {
        println("LifecycleClassVisitor : visitMethod : $name")
        val methodVisitor =
            cv.visitMethod(access, name, descriptor, signature, exceptions)
        if (mClassName == "androidx/fragment/app/FragmentActivity") {
            if (name == "onCreate") {
                println("LifecycleClassVisitor : change method ----> $name")
                return LifecycleOnCreateMethodVisitor(methodVisitor)
            }
        }
        return methodVisitor
    }

    override fun visitEnd() {
        println("Gio : visit -----> end")
        super.visitEnd()
    }
}