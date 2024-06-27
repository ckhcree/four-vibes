package com.kita.fourvibes.domain.comment.controller.request

import com.kita.fourvibes.domain.comment.repository.model.Comment
import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val ment: String,
    val createdAt: LocalDateTime?,
    var updatedAt: LocalDateTime?,
) {
    companion object {
        fun from(comment: Comment): CommentResponse {
            return CommentResponse(
                id = comment.id!!,
                ment = comment.ment,
                createdAt = comment.createdAt,
                updatedAt = comment.updatedAt,
            )
        }
    }
}
