package com.kita.fourvibes.domain.bullet.service

import com.kita.fourvibes.domain.bullet.controller.request.BulletResponse
import com.kita.fourvibes.domain.bullet.controller.request.BulletResponseWithComments
import com.kita.fourvibes.domain.bullet.controller.request.CreateBulletRequest
import com.kita.fourvibes.domain.bullet.controller.request.DeleteBulletRequest
import com.kita.fourvibes.domain.bullet.controller.request.ThumbsUpRequest
import com.kita.fourvibes.domain.bullet.controller.request.UpdateBulletRequest
import com.kita.fourvibes.domain.bullet.repository.BulletRepository
import com.kita.fourvibes.domain.bullet.repository.model.Bullet
import com.kita.fourvibes.domain.bullet.repository.model.thumbsup.ThumbsUp
import com.kita.fourvibes.domain.bullet.repository.model.thumbsup.ThumbsUpId
import com.kita.fourvibes.domain.bullet.repository.thumbsup.ThumbsUpRepository
import com.kita.fourvibes.domain.comment.repository.CommentRepository
import com.kita.fourvibes.repository.UserRepository
import com.kita.fourvibes.security.JwtProvider
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BulletService(
    private val thumbsUpRepository: ThumbsUpRepository,
    private val commentRepository: CommentRepository,
    private val bulletRepository: BulletRepository,
    private val userRepository: UserRepository,
    private val jwtProvider: JwtProvider,
) {

    fun getAllBullets(): List<BulletResponse>? {
        val foundAllBullets = bulletRepository.findAllByOrderByCreatedAtDesc()
        return foundAllBullets.map { BulletResponse.from(it) }
    }

    @Transactional
    fun getBulletById(id: Long): BulletResponseWithComments {
        val foundBullet = bulletRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("Bullet not found")

        val commentList = commentRepository.findAllByBulletId(id)

        return BulletResponseWithComments.from(foundBullet, commentList)
    }

    @Transactional
    fun createBullet(createBulletRequest: CreateBulletRequest): BulletResponse {

        val userId = extractUserIdFromToken(createBulletRequest.token)

        val user = userRepository.findByIdOrNull(userId)
            ?: throw IllegalArgumentException("유효한 토큰 사용자만 게시글 작성 가능")

        val bullet = Bullet(createBulletRequest.nickname, createBulletRequest.title, createBulletRequest.memo, user)

        val createdBullet = bulletRepository.save(bullet)

        return BulletResponse.from(createdBullet)
    }

    @Transactional
    fun updateBullet(id: Long, updateBulletRequest: UpdateBulletRequest): BulletResponse? {

        val userId = extractUserIdFromToken(updateBulletRequest.token)

        val foundBullet = bulletRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("Bullet not found")

        // 파운드 불릿의 유저아이디랑 , 토큰의 유저아이디가 동일해야함
        if (foundBullet.user.id != userId) {
            throw IllegalArgumentException("해당 사용자가 작성한 게시글만 수정 가능")
        }

        foundBullet.updateBullet(updateBulletRequest)

        return foundBullet.let { BulletResponse.from(it) }
    }

    @Transactional
    fun deleteBullet(id: Long, deleteBulletRequest: DeleteBulletRequest) {

        val userId = extractUserIdFromToken(deleteBulletRequest.token)

        val foundBullet = bulletRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("Bullet not found")

        // 파운드 불릿의 유저아이디랑 , 토큰의 유저아이디가 동일해야함
        if (foundBullet.user.id != userId) {
            throw IllegalArgumentException("해당 사용자가 작성한 게시글만 삭제 가능")
        }

        // 불릿 삭제 전 , 불릿에 딸린 댓글들 모두 삭제
        val commentList = commentRepository.findAllByBulletId(id)
        commentRepository.deleteAll(commentList)

        bulletRepository.deleteById(id)
    }

    @Transactional
    fun thumbsUpBullet(bulletId: Long, thumbsUpRequest: ThumbsUpRequest) {

        val userId = extractUserIdFromToken(thumbsUpRequest.token)

        val bullet = bulletRepository.findByIdOrNull(bulletId)
            ?: throw IllegalArgumentException("Bullet not found")
        val user = userRepository.findByIdOrNull(userId)
            ?: throw IllegalArgumentException("User not found")

        val thumbsUpId = ThumbsUpId(bulletId = bulletId, userId = userId)
        val thumbsUp = ThumbsUp(id = thumbsUpId, bullet = bullet, user = user)

        thumbsUpRepository.save(thumbsUp)
    }

    @Transactional
    fun cancelThumbsUpBullet(bulletId: Long, thumbsUpRequest: ThumbsUpRequest) {

        val userId = extractUserIdFromToken(thumbsUpRequest.token)

        val thumbsUpId = ThumbsUpId(bulletId = bulletId, userId = userId)
        val thumbsUp = thumbsUpRepository.findByIdOrNull(thumbsUpId)
            ?: throw IllegalArgumentException("ThumbsUp not found")

        thumbsUpRepository.delete(thumbsUp)
    }

    private fun extractUserIdFromToken(token: String): Long {
        val validation = jwtProvider.validateToken(token)
        if (validation.isFailure) {
            throw IllegalArgumentException("유효한 토큰일 경우에만 작업 가능")
        }
        return validation.getOrNull()!!.payload.subject.toLong()
    }
}
