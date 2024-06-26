package com.kita.fourvibes.domain.bullet.repository.model

import com.kita.fourvibes.domain.bullet.controller.request.UpdateBulletRequest
import com.kita.fourvibes.repository.model.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "bullets")
class Bullet(
    @Column
    var nickname: String,

    @Column
    var title: String,

    @Column
    var memo: String,

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val user: User,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()

    fun updateBullet(updateBulletRequest: UpdateBulletRequest) {
        nickname = updateBulletRequest.nickname
        title = updateBulletRequest.title
        memo = updateBulletRequest.memo
    }
}