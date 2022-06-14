package com.test.persontest.model.video

data class Volume(
    val measured_i: Double,
    val measured_lra: Double,
    val measured_threshold: Double,
    val measured_tp: Double,
    val target_i: Int,
    val target_offset: Double,
    val target_tp: Int
)