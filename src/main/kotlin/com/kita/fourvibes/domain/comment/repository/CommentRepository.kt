package com.kita.fourvibes.domain.comment.repository

import com.kita.fourvibes.domain.comment.repository.model.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long> {
    fun findAllByBulletId(bulletId: Long): List<Comment>
}