package com.kita.fourvibes.domain.bullet.controller.request

import com.kita.fourvibes.domain.bullet.repository.model.Bullet
import com.kita.fourvibes.domain.comment.repository.model.Comment
import java.time.LocalDateTime

data class BulletResponseWithComments(
    val id: Long,
    val nickname: String,
    val title: String,
    val memo: String,
    val createdAt: LocalDateTime?,
    var updatedAt: LocalDateTime?,
    val commentList: List<Comment>
) {
    companion object {
        fun from(
            bullet: Bullet, commentList: List<Comment>
        ): BulletResponseWithComments {
            return BulletResponseWithComments(
                id = bullet.id!!,
                nickname = bullet.nickname,
                title = bullet.title,
                memo = bullet.memo,
                createdAt = bullet.createdAt,
                updatedAt = bullet.updatedAt,
                commentList = commentList,
            )
        }
    }
}