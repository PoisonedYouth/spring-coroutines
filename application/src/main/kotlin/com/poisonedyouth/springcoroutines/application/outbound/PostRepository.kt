package com.poisonedyouth.springcoroutines.application.outbound

import com.poisonedyouth.coroutines.domain.model.Post
import com.poisonedyouth.coroutines.domain.service.port.PostRepositoryPort
import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Instant
import java.util.UUID
import java.util.function.BiFunction

@Repository
class PostRepository(
    private val databaseClient: DatabaseClient
) : PostRepositoryPort {

    val mapping: BiFunction<Row, RowMetadata, Post> = BiFunction<Row, RowMetadata, Post> { row: Row, _: RowMetadata? ->
        Post(
            id = row.getOrThrow("id", UUID::class.java),
            title = row.getOrThrow("title", String::class.java),
            content = row.getOrThrow("content", String::class.java),
            author = row.getOrThrow("author", String::class.java),
            createdAt = row.getOrThrow("created_at", Instant::class.java),
            updatedAt = row.getOrThrow("updated_at", Instant::class.java)
        )
    }

    private fun <T> Row.getOrThrow(name: String, type: Class<T>): T {
        return this.get(name, type) ?: error("Row with name '$name' not found.")
    }


    override fun findById(id: UUID): Mono<Post?> {
        return databaseClient
            .sql("SELECT * FROM Post WHERE id=:id")
            .bind("id", id)
            .map(mapping)
            .one()
    }

    override fun findAll(): Flux<Post> {
        return databaseClient
            .sql("SELECT * FROM Post")
            .map(mapping)
            .all()
    }

    override fun save(post: Post): Mono<UUID> {
        return databaseClient
            .sql("INSERT INTO  Post (id, title, content, author, created_at, updated_at) VALUES (:id, :title, :content, :author, :createdAt, :updatedAt)")
            .filter { statement -> statement.returnGeneratedValues("id") }
            .bind("id", post.id)
            .bind("title", post.title)
            .bind("content", post.content)
            .bind("author", post.author)
            .bind("createdAt", post.createdAt)
            .bind("updatedAt", post.updatedAt)
            .fetch()
            .first()
            .map { result -> result["id"] as UUID }
    }
}