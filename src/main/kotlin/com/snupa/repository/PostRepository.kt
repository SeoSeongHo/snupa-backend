package com.snupa.repository

import com.snupa.domain.Like
import com.snupa.domain.Post
import com.snupa.util.EnumCollection
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long> {
    fun findByCategory(category: EnumCollection.Category): MutableList<Post>
}