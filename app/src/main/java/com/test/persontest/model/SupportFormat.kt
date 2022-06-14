package com.test.persontest.model

data class SupportFormat(
    val codecs: List<String>,
    val display_desc: String,
    val format: String,
    val new_description: String,
    val quality: Int,
    val superscript: String
)