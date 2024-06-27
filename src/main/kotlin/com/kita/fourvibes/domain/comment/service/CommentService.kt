package com.kita.fourvibes.domain.comment.service

import com.kita.fourvibes.domain.bullet.repository.BulletRepository
import com.kita.fourvibes.domain.comment.controller.request.CommentRequest
import com.kita.fourvibes.domain.comment.controller.request.CommentResponse
import com.kita.fourvibes.domain.comment.repository.CommentRepository
import com.kita.fourvibes.repository.UserRepository
import com.kita.fourvibes.security.JwtProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val bulletRepository: BulletRepository,
    private val userRepository: UserRepository,
    private val jwtProvider: JwtProvider
) {

    @Transactional
    fun createComment(commentRequest: CommentRequest): CommentResponse {
        TODO()
    }

    @Transactional
    fun updateComment(id: Long, commentRequest: CommentRequest): CommentResponse {
        TODO()
    }

    @Transactional
    fun deleteComment(id: Long, commentRequest: CommentRequest): CommentResponse {
        TODO()
    }
}