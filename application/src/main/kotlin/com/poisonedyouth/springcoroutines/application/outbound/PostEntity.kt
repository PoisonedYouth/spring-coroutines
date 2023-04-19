package com.poisonedyouth.springcoroutines.application.outbound

import com.poisonedyouth.coroutines.domain.model.Post
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant
import java.util.UUID

@Table(name = "Post")
@Entity
data class PostEntity(
    @Id
    var id: UUID,
    var title: String,
    var content: String,
    var author: String,
    var createdAt: Instant,
    var updatedAt: Instant
)

fun PostEntity.toPost() = Post(
    id = this.id,
    title = this.title,
    content = this.content,
    author = this.author,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

fun Post.toPostEntity() = PostEntity(
    id = this.id,
    title = this.title,
    content = this.content,
    author = this.author,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)