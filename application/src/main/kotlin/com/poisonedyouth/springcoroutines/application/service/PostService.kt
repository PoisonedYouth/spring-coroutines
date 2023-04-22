package com.poisonedyouth.springcoroutines.application.service

import com.poisonedyouth.coroutines.domain.model.Post
import com.poisonedyouth.coroutines.domain.service.port.PostRepositoryPort
import com.poisonedyouth.coroutines.domain.service.port.PostServicePort
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PostService(
    private val postRepository: PostRepositoryPort
) : PostServicePort {

    override suspend fun getPost(id: UUID): Post? {
        return postRepository.findById(id)
    }

    override suspend fun getAllPosts(): Flow<Post> {
        return postRepository.findAll()
    }

    override suspend fun createPost(post: Post): UUID {
        return postRepository.save(post)
    }
}