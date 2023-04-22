package com.poisonedyouth.springcoroutines.application.inbound

import com.poisonedyouth.coroutines.domain.model.Post
import com.poisonedyouth.coroutines.domain.service.port.PostServicePort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.util.UUID

@RestController
@RequestMapping("/api/posts")
class PostController(
    private val postService: PostServicePort
) {

    @GetMapping
    suspend fun getAllPosts() = postService.getAllPosts()

    @GetMapping("/{id}")
    suspend fun getPostById(@PathVariable id: UUID) = postService.getPost(id)

    @PostMapping
    suspend fun createPost(@RequestBody post: PostDto) = postService.createPost(post.toPost())
}

private fun PostDto.toPost(): Post {
    val new = Instant.now()
    return Post(
        id = UUID.randomUUID(),
        title = title,
        content = content,
        author = author,
        createdAt = new,
        updatedAt = new
    )
}