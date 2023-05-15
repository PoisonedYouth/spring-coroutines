package com.poisonedyouth.springcoroutines.application.inbound

import com.poisonedyouth.coroutines.domain.model.Post
import java.time.Instant
import java.util.UUID

data class PostDto(
    val title: String,
    val content: String,
    val author: String,
)

fun PostDto.toPost(): Post {
    val new = Instant.now()
    return Post(
        id = UUID.randomUUID(),
        title = title,
        content = content,
        author = author,
        createdAt = new,
        updatedAt = new
    )
}

fun Post.toPostDto() = PostDto(
    title = this.title,
    content = this.content,
    author = this.author
)