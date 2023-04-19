package com.poisonedyouth.coroutines.domain.service.port

import com.poisonedyouth.coroutines.domain.model.Post
import java.util.UUID

interface PostRepositoryPort {

    fun findById(id: UUID): Post?
    fun findAll(): List<Post>
    fun save(post: Post): UUID
}