package com.hd.plugin

import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle
import org.gradle.api.tasks.TaskState

class TimeListener implements TaskExecutionListener, BuildListener {

    private Long current
    private times = []

    @Override
    void buildStarted(Gradle gradle) {

    }

    @Override
    void settingsEvaluated(Settings settings) {

    }

    @Override
    void projectsLoaded(Gradle gradle) {

    }

    @Override
    void projectsEvaluated(Gradle gradle) {

    }

    @Override
    void buildFinished(BuildResult result) {
        println "Task spend time:"
        for (time in times) {
            printf "%7sms  %s\n", time
        }
    }

    @Override
    void beforeExecute(Task task) {
        current = System.currentTimeMillis()
    }

    @Override
    void afterExecute(Task task, TaskState state) {
        def ms = System.currentTimeMillis() - current
        current = System.currentTimeMillis()
        times.add([ms, task.path])
//        task.project.logger.warn "${task.path} spend ${ms}ms"
    }
}