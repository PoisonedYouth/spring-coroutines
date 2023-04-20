package com.poisonedyouth.coroutines.domain.service.port

import com.poisonedyouth.coroutines.domain.model.Post
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

interface PostServicePort {
    fun getPost(id: UUID): Mono<Post?>
    fun getAllPosts(): Flux<Post>
    fun createPost(post: Post): Mono<UUID>
}