package com.kita.fourvibes.domain.comment.service

import com.kita.fourvibes.domain.bullet.repository.BulletRepository
import com.kita.fourvibes.domain.comment.controller.request.CommentRequest
import com.kita.fourvibes.domain.comment.controller.request.CommentResponse
import com.kita.fourvibes.domain.comment.repository.CommentRepository
import com.kita.fourvibes.domain.comment.repository.model.Comment
import com.kita.fourvibes.repository.UserRepository
import com.kita.fourvibes.security.JwtProvider
import org.springframework.data.repository.findByIdOrNull
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
    fun createComment(bulletId: Long, commentRequest: CommentRequest): CommentResponse {

        val userId = extractUserIdFromToken(commentRequest.token)

        val user = userRepository.findByIdOrNull(userId)
            ?: throw IllegalArgumentException("유효한 토큰 사용자만 댓글 작성 가능")

        val foundBullet = bulletRepository.findByIdOrNull(bulletId)
            ?: throw IllegalArgumentException("유효한 게시글에만 댓글 작성 가능")

        val comment = Comment(commentRequest.ment, foundBullet, user)

        val createdComment = commentRepository.save(comment)

        return CommentResponse.from(createdComment)
    }

    @Transactional
    fun updateComment(id: Long, commentRequest: CommentRequest): CommentResponse? {

        val userId = extractUserIdFromToken(commentRequest.token)

        val foundComment = commentRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("Comment not found")

        // 파운드 코멘트의 유저아이디랑, 토큰의 유저아이디가 동일해야함
        if (foundComment.user.id != userId) {
            throw IllegalArgumentException("해당 사용자가 작성한 댓글만 수정 가능")
        }

        foundComment.updateComment(commentRequest)

        return foundComment.let { CommentResponse.from(it) }
    }

    @Transactional
    fun deleteComment(id: Long, commentRequest: CommentRequest) {

        val userId = extractUserIdFromToken(commentRequest.token)

        val foundComment = commentRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("Comment not found")

        // 파운드 코멘트의 유저아이디랑, 토큰의 유저아이디가 동일해야함
        if (foundComment.user.id != userId) {
            throw IllegalArgumentException("해당 사용자가 작성한 댓글만 삭제 가능")
        }

        commentRepository.deleteById(id)
    }

    private fun extractUserIdFromToken(token: String): Long {
        val validation = jwtProvider.validateToken(token)
        if (validation.isFailure) {
            throw IllegalArgumentException("유효한 토큰일 경우에만 작업 가능")
        }
        return validation.getOrNull()!!.payload.subject.toLong()
    }
}