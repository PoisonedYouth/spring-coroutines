package com.poisonedyouth.springcoroutines.application.service

import com.poisonedyouth.coroutines.domain.model.Post
import com.poisonedyouth.coroutines.domain.service.port.PostRepositoryPort
import com.poisonedyouth.coroutines.domain.service.port.PostServicePort
import com.poisonedyouth.springcoroutines.application.inbound.ExternalApi1
import com.poisonedyouth.springcoroutines.application.inbound.ExternalApi2
import com.poisonedyouth.springcoroutines.application.inbound.toPost
import org.springframework.stereotype.Service
import java.util.UUID


@Service
class PostService(
    private val postRepository: PostRepositoryPort,
    private val externalApi1: ExternalApi1,
    private val externalApi2: ExternalApi2
) : PostServicePort {

    override fun getPost(id: UUID): Post? {
        return postRepository.findById(id)
    }

    override fun getAllPosts(): List<Post> {
        return postRepository.findAll()
    }

    override fun createPost(post: Post): UUID {
        return postRepository.save(post)
    }

    override fun getAllPostsAggregated(): List<Post> {
        return externalApi1.getPosts().map { it.toPost() } + externalApi2.getPosts().map { it.toPost() }
    }
}