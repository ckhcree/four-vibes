package com.kita.fourvibes.domain.bullet.service

import com.kita.fourvibes.domain.bullet.controller.request.BulletResponse
import com.kita.fourvibes.domain.bullet.controller.request.CreateBulletRequest
import com.kita.fourvibes.domain.bullet.controller.request.DeleteBulletRequest
import com.kita.fourvibes.domain.bullet.controller.request.UpdateBulletRequest
import com.kita.fourvibes.domain.bullet.repository.BulletRepository
import com.kita.fourvibes.domain.bullet.repository.model.Bullet
import com.kita.fourvibes.repository.UserRepository
import com.kita.fourvibes.security.JwtProvider
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BulletService(
    private val bulletRepository: BulletRepository,
    private val userRepository: UserRepository,
    private val jwtProvider: JwtProvider,
) {

    fun findAllBullets(): List<BulletResponse>? {
        val foundAllBullets = bulletRepository.findAllByOrderByCreatedAtDesc()
        return foundAllBullets.map { BulletResponse.from(it) }
    }

    @Transactional
    fun findBulletById(id: Long): BulletResponse? {
        val foundBullet = bulletRepository.findByIdOrNull(id)
        return foundBullet?.let { BulletResponse.from(it) }
    }

    @Transactional
    fun createBullet(createBulletrequest: CreateBulletRequest): BulletResponse {
        val validation = jwtProvider.validateToken(createBulletrequest.token)
        if (validation.isFailure) {
            throw IllegalArgumentException("유효한 토큰일 경우에만 게시글 작성 가능")
        }

        val user = userRepository.findByIdOrNull(validation.getOrNull()?.payload?.subject?.toLong())
            ?: throw IllegalArgumentException("유효한 토큰 사용자만 게시글 작성 가능")

        val bullet = Bullet(createBulletrequest.nickname, createBulletrequest.title, createBulletrequest.memo, user)

        val createdBullet = bulletRepository.save(bullet)

        return BulletResponse.from(createdBullet)
    }

    @Transactional
    fun updateBullet(id: Long, updateBulletRequest: UpdateBulletRequest): BulletResponse? {
        val validation = jwtProvider.validateToken(updateBulletRequest.token)
        if (validation.isFailure) {
            throw IllegalArgumentException("유효한 토큰일 경우에만 게시글 수정 가능")
        }

        val foundBullet = bulletRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("Bullet not found")

        // 파운드 불릿의 유저아이디랑 , 토큰의 유저아이디가 동일해야함
        if (foundBullet.user.id != validation.getOrNull()?.payload?.subject?.toLong()) {
            throw IllegalArgumentException("해당 사용자가 작성한 게시글만 수정 가능")
        }

        foundBullet.updateBullet(updateBulletRequest)

        return foundBullet.let { BulletResponse.from(it) }
    }

    @Transactional
    fun deleteBullet(id: Long, deleteBulletRequest: DeleteBulletRequest) {
        val validation = jwtProvider.validateToken(deleteBulletRequest.token)
        if (validation.isFailure) {
            throw IllegalArgumentException("유효한 토큰일 경우에만 게시글 삭제 가능")
        }

        val foundBullet = bulletRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("Bullet not found")

        // 파운드 불릿의 유저아이디랑 , 토큰의 유저아이디가 동일해야함
        if (foundBullet.user.id != validation.getOrNull()?.payload?.subject?.toLong()) {
            throw IllegalArgumentException("해당 사용자가 작성한 게시글만 삭제 가능")
        }

        bulletRepository.deleteById(id)
    }

    @Transactional
    fun thumbsUp() {
    }

    @Transactional
    fun cancelThumbsUp() {
    }
}
