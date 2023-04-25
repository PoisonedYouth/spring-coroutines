package com.poisonedyouth.springcoroutines.application.inbound

import com.poisonedyouth.coroutines.domain.model.Post
import com.poisonedyouth.coroutines.domain.service.port.PostServicePort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.time.Instant
import java.util.UUID

@RestController
@RequestMapping("/api/posts")
class PostController(
    private val postService: PostServicePort
) {

    @GetMapping
    fun getAllPosts() = postService.getAllPosts()

    @GetMapping("/{id}")
    fun getPostById(@PathVariable id: UUID) = postService.getPost(id)

    @PostMapping
    fun createPost(@RequestBody post: Mono<PostDto>) = postService.createPost(post.toPost())
}

private fun Mono<PostDto>.toPost(): Mono<Post> {
    val new = Instant.now()
    return this.map {
        Post(
            id = UUID.randomUUID(),
            title = it.title,
            content = it.content,
            author = it.author,
            createdAt = new,
            updatedAt = new
        )
    }
}