package com.kita.fourvibes.domain.comment.controller.request

data class CommentRequest(
    val bulletId: Long,
    val ment: String,
    val token: String,
)
