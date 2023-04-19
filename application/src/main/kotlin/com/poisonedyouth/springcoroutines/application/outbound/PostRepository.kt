package com.poisonedyouth.springcoroutines.application.outbound

import com.poisonedyouth.coroutines.domain.model.Post
import com.poisonedyouth.coroutines.domain.service.port.PostRepositoryPort
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.TypedQuery
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Root
import org.springframework.stereotype.Repository
import java.util.UUID


@Repository
class PostRepository: PostRepositoryPort {

    @PersistenceContext
    private lateinit var entityManager: EntityManager


    override fun findById(id: UUID): Post? {
       return entityManager.find(PostEntity::class.java, id).toPost()
    }

    override fun findAll(): List<Post> {
        val cb: CriteriaBuilder = entityManager.criteriaBuilder
        val cq: CriteriaQuery<PostEntity> = cb.createQuery(PostEntity::class.java)
        val rootEntry: Root<PostEntity> = cq.from(PostEntity::class.java)
        val all: CriteriaQuery<PostEntity> = cq.select(rootEntry)
        val allQuery: TypedQuery<PostEntity> = entityManager.createQuery(all)
        return allQuery.resultList.map { it.toPost() }
    }

    override fun save(post: Post): UUID {
        entityManager.persist(post.toPostEntity())
        entityManager.flush()
        return post.id
    }
}