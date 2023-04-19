package com.poisonedyouth.springcoroutines.application.inbound

data class PostDto(
    val title: String,
    val content: String,
    val author: String,
)