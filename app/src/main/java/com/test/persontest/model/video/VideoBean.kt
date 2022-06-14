package com.test.persontest.model.video

data class VideoBean(
    val code: Int,
    val `data`: Data,
    val message: String,
    val session: String,
    val ttl: Int
)