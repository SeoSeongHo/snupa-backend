package com.snupa.repository

import com.snupa.domain.Like
import org.springframework.data.jpa.repository.JpaRepository

interface LikeRepository : JpaRepository<Like, Long> {
    fun findByUserId(userId: Long): List<Like>
}