package com.poisonedyouth.coroutines.domain.service.port

import com.poisonedyouth.coroutines.domain.model.Post
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

interface PostRepositoryPort {

    fun findById(id: UUID): Mono<Post?>
    fun findAll(): Flux<Post>
    fun save(post: Mono<Post>): Mono<UUID>
}