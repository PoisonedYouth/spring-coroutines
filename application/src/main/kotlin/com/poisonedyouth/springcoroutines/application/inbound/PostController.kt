package com.poisonedyouth.springcoroutines.application.inbound

import com.poisonedyouth.coroutines.domain.service.port.PostServicePort
import kotlinx.coroutines.flow.map
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

//@RestController
@RequestMapping("/api/posts")
class PostController(
    private val postService: PostServicePort
) {

    @GetMapping
    suspend fun getAllPosts() = ResponseEntity.ok()

    @GetMapping("/{id}")
    suspend fun getPostById(@PathVariable id: UUID) = postService.getPost(id)?.toPostDto()

    @PostMapping
    suspend fun createPost(@RequestBody post: PostDto) = postService.createPost(post.toPost())
}

