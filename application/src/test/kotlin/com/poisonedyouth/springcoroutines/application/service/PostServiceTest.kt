package com.poisonedyouth.springcoroutines.application.service

import com.poisonedyouth.coroutines.domain.service.port.PostRepositoryPort
import com.poisonedyouth.springcoroutines.application.inbound.ExternalApi1
import com.poisonedyouth.springcoroutines.application.inbound.ExternalApi2
import com.poisonedyouth.springcoroutines.application.inbound.PostDto
import com.poisonedyouth.springcoroutines.application.inbound.toPost
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class PostServiceTest {

    private val postRepository: PostRepositoryPort = mock(PostRepositoryPort::class.java)
    private val externalApi1: ExternalApi1 = mock(ExternalApi1::class.java)
    private val externalApi2: ExternalApi2 = mock(ExternalApi2::class.java)
    private val postService = PostService(
        postRepository,
        externalApi1,
        externalApi2
    )

    @Test
    fun `getAggregatedPosts returns sum of both api result`() = runTest {
        // given
        val postDto1 =  PostDto(
            title = "My title 2",
            content = "My Content 2",
            author = "Me 2"
        )
        val postDto2 =   PostDto(
            title = "My title",
            content = "My Content",
            author = "Me"
        )

        whenever(externalApi2.getPosts()).thenReturn(
            async{
                listOf(
                   postDto1
                )
            }
        )

        whenever(externalApi1.getPosts()).thenReturn(
            async{
                listOf(
                  postDto2
                )
            }
        )

        // when
        val result = postService.getAggregatedPosts()

        // then
        assertThat(result.toList()).extracting("title").containsExactlyInAnyOrder(postDto1.title, postDto2.title)
    }
}