package com.poisonedyouth.springcoroutines.application.service

import com.poisonedyouth.coroutines.domain.model.Post
import com.poisonedyouth.coroutines.domain.service.port.PostRepositoryPort
import com.poisonedyouth.coroutines.domain.service.port.PostServicePort
import com.poisonedyouth.springcoroutines.application.inbound.ExternalApi1
import com.poisonedyouth.springcoroutines.application.inbound.ExternalApi2
import com.poisonedyouth.springcoroutines.application.inbound.toPost
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PostService(
    private val postRepository: PostRepositoryPort,
    private val externalApi1: ExternalApi1,
    private val externalApi2: ExternalApi2
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

    override suspend fun getAggregatedPosts(): Flow<Post> {
        val (a, b) = awaitAll(externalApi1.getPosts(), externalApi2.getPosts())
        return (a.map { it.toPost() } + b.map { it.toPost() }).asFlow()
    }
}