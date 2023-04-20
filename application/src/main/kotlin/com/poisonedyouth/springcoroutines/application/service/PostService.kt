package com.poisonedyouth.springcoroutines.application.service

import com.poisonedyouth.coroutines.domain.model.Post
import com.poisonedyouth.coroutines.domain.service.port.PostRepositoryPort
import com.poisonedyouth.coroutines.domain.service.port.PostServicePort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID


@Service
class PostService(
    private val postRepository: PostRepositoryPort
) : PostServicePort {

    override fun getPost(id: UUID): Mono<Post?> {
        return postRepository.findById(id)
    }

    override fun getAllPosts(): Flux<Post> {
        return postRepository.findAll()
    }

    override fun createPost(post: Post): Mono<UUID> {
        return postRepository.save(post)
    }
}