package com.kita.fourvibes.domain.comment.controller.request

import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val ment: String,
    val createdAt: LocalDateTime?,
    var updatedAt: LocalDateTime?,
)
