package com.poisonedyouth.coroutines.domain.service.port

import com.poisonedyouth.coroutines.domain.model.Post
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface PostServicePort {
    suspend fun getPost(id: UUID): Post?
    suspend fun getAllPosts(): Flow<Post>
    suspend fun createPost(post: Post): UUID

    suspend fun getAggregatedPosts(): Flow<Post>
}