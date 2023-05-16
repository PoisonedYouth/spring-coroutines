package com.poisonedyouth.springcoroutines.application.service

import com.poisonedyouth.coroutines.domain.model.Post
import com.poisonedyouth.coroutines.domain.service.port.PostRepositoryPort
import com.poisonedyouth.coroutines.domain.service.port.PostServicePort
import com.poisonedyouth.springcoroutines.application.inbound.ExternalApi1
import com.poisonedyouth.springcoroutines.application.inbound.ExternalApi2
import com.poisonedyouth.springcoroutines.application.inbound.toPost
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.UUID
import java.util.concurrent.Callable
import java.util.concurrent.CancellationException
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


@Service
class PostService(
    private val postRepository: PostRepositoryPort,
    private val externalApi1: ExternalApi1,
    private val externalApi2: ExternalApi2
) : PostServicePort {
    private val logger = LoggerFactory.getLogger(PostService::class.java)

    private val executorService = Executors.newCachedThreadPool()

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
        return try {
            val callable1 = Callable { externalApi1.getPosts().map { it.toPost() } }
            val callable2 = Callable { externalApi2.getPosts().map { it.toPost() } }
            val result = executorService.invokeAll(listOf(callable1, callable2), 4, TimeUnit.SECONDS)
            result.flatMap { it.get() }
        } catch (e: CancellationException) {
            logger.error("Failed to get result in time.", e)
            emptyList()
        }
    }
}