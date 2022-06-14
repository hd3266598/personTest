package com.test.persontest.model

data class Dash(
    val audio: List<Audio>,
    val dolby: Any,
    val duration: Int,
    val minBufferTime: Double,
    val min_buffer_time: Double,
    val video: List<Video>
)