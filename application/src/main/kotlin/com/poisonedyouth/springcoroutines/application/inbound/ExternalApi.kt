package com.poisonedyouth.springcoroutines.application.inbound

import org.springframework.stereotype.Component

interface ExternalApi1 {

    fun getPosts(): List<PostDto>
}

@Component
class ExternalApi1Impl : ExternalApi1 {
    override fun getPosts() =
        listOf(
            PostDto(
                title = "My title",
                content = "My Content",
                author = "Me"
            )
        )
}

interface ExternalApi2 {

    fun getPosts(): List<PostDto>
}

@Component
class ExternalApi2Impl : ExternalApi2 {
    override fun getPosts() =
        listOf(
            PostDto(
                title = "My title 2",
                content = "My Content 2",
                author = "Me 2"
            )
        )
}