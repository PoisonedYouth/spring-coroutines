package com.poisonedyouth.coroutines.domain.service.port

import com.poisonedyouth.coroutines.domain.model.Post
import java.util.UUID

interface PostServicePort {
    fun getPost(id: UUID): Post?
    fun getAllPosts(): List<Post>
    fun createPost(post: Post): UUID
    fun getAllPostsAggregated(): List<Post>
}