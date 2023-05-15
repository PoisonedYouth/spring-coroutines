package com.poisonedyouth.springcoroutines.application.inbound

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import org.springframework.stereotype.Component

interface ExternalApi1 {

    fun getPosts(): Deferred<List<PostDto>>
}

@Component
class ExternalApi1Impl : ExternalApi1 {
    override fun getPosts() = CoroutineScope(SupervisorJob()).async {
        delay(5000)
        listOf(
            PostDto(
                title = "My title",
                content = "My Content",
                author = "Me"
            )
        )
    }

}

interface ExternalApi2 {

    fun getPosts(): Deferred<List<PostDto>>
}

@Component
class ExternalApi2Impl : ExternalApi2 {
    override fun getPosts() = CoroutineScope(SupervisorJob()).async {
        listOf(
            PostDto(
                title = "My title 2",
                content = "My Content 2",
                author = "Me 2"
            )
        )
    }

}