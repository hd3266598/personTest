package com.hd.plugin.lifecycle;

import groovyjarjarasm.asm.ClassVisitor;
import groovyjarjarasm.asm.MethodVisitor;
import groovyjarjarasm.asm.Opcodes;

public class LifecycleClassVisitor extends ClassVisitor implements Opcodes {
    private String mClassName;

    public LifecycleClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        System.out.println("LifecycleClassVisitor : visit -----> started :" + name);
        mClassName = name;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        System.out.println("LifecycleClassVisitor : visitMethod : " + name);
        MethodVisitor methodVisitor = cv.visitMethod(access, name, descriptor, signature, exceptions);
        if (mClassName.equals("androidx/fragment/app/FragmentActivity")) {
            if (name.equals("onCreate")) {
                System.out.println("LifecycleClassVisitor : change method ----> " + name);
                return new LifecycleOnCreateMethodVisitor(methodVisitor);
            }
        }
        return methodVisitor;
    }

    @Override
    public void visitEnd() {
        System.out.println("Gio : visit -----> end");
        super.visitEnd();
    }
}
