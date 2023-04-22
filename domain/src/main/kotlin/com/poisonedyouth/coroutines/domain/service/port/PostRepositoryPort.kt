package com.poisonedyouth.coroutines.domain.service.port

import com.poisonedyouth.coroutines.domain.model.Post
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface PostRepositoryPort {

    suspend fun findById(id: UUID): Post?
    suspend fun findAll(): Flow<Post>
    suspend fun save(post: Post): UUID
}