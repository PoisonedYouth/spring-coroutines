package com.poisonedyouth.springcoroutines.application.inbound

import com.poisonedyouth.coroutines.domain.service.port.PostServicePort
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait
import org.springframework.web.reactive.function.server.json
import java.net.URI
import java.util.UUID

@Component
class PostHandler(
    private val postService: PostServicePort
) {

    suspend fun getAllPosts(serverRequest: ServerRequest): ServerResponse {
        return ServerResponse.ok()
            .json()
            .bodyAndAwait(postService.getAllPosts().map { it.toPostDto() })
    }

    suspend fun getPostById(serverRequest: ServerRequest): ServerResponse {
        val id = UUID.fromString(serverRequest.pathVariable("id"))
        val post = postService.getPost(id)
        return if (post != null) {
            ServerResponse.ok()
                .json()
                .bodyValueAndAwait(post.toPostDto())
        } else {
            ServerResponse.notFound().buildAndAwait()
        }
    }

    suspend fun createPost(serverRequest: ServerRequest): ServerResponse {
        val post = postService.createPost(serverRequest.awaitBody(PostDto::class).toPost())
        return ServerResponse.created(URI.create("/api/posts/$post")).json().bodyValueAndAwait(post)
    }
}