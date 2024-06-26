package com.kita.fourvibes.domain.bullet.controller.request

import com.kita.fourvibes.domain.bullet.repository.model.Bullet
import java.time.LocalDateTime

data class BulletResponse(
    val id: Long,
    val nickname: String,
    val title: String,
    val memo: String,
    val createdAt: LocalDateTime?,
    var updatedAt: LocalDateTime?,
) {
    companion object {
        fun from(bullet: Bullet): BulletResponse {
            return BulletResponse(
                id = bullet.id!!,
                nickname = bullet.nickname,
                title = bullet.title,
                memo = bullet.memo,
                createdAt = bullet.createdAt,
                updatedAt = bullet.updatedAt,
            )
        }
    }
}