package com.poisonedyouth.springcoroutines.application.inbound

import com.poisonedyouth.coroutines.domain.service.port.PostServicePort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/posts")
class PostController(
    private val postService: PostServicePort
) {
    @GetMapping("/aggregated")
    fun getAllPostsAggregated() = postService.getAllPostsAggregated()

    @GetMapping
    fun getAllPosts() = postService.getAllPosts()

    @GetMapping("/{id}")
    fun getPostById(@PathVariable id: UUID) = postService.getPost(id)

    @PostMapping
    fun createPost(@RequestBody post: PostDto) = postService.createPost(post.toPost())
}