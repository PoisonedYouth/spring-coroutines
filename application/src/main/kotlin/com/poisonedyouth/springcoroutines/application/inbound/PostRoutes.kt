package com.poisonedyouth.springcoroutines.application.inbound

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class PostRoutes {

    @Bean
    fun allPostsRouterFunction(postHandler: PostHandler) = coRouter {
        "api/posts".nest {
            GET("", postHandler::getAllPosts)
            GET("{id}", postHandler::getPostById)
            POST("", postHandler::createPost)
        }
    }
}