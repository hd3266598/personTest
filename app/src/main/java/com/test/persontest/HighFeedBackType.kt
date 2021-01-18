package com.test.persontest

enum class HighFeedbackType() {
    //未压线状态
    NONE,

    //压线状态
    PRESSURE_LINE,

    //驶过线段
    PRESSURE_LINE_COMPLETE,
}