package com.nmpubaya.cerbung

data class Cerbung(
    val url: String,
    val title: String,
    val author: String,
    val permission: Boolean,
    val icon_list: Int,
    val icon_like: Int,
    val genre: String,
    val short_description: String,
    val story: String
)
