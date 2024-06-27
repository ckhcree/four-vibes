package com.kita.fourvibes.domain.comment.repository.model

import com.kita.fourvibes.domain.bullet.repository.model.Bullet
import com.kita.fourvibes.domain.comment.controller.request.CommentRequest
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
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "comments")
class Comment(
    @Column
    var ment: String,

    @JoinColumn(name = "bullet_id")
    @ManyToOne(fetch = FetchType.LAZY)
    var bullet: Bullet,

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

    @UpdateTimestamp
    @Column(nullable = true, updatable = true)
    var updatedAt: LocalDateTime? = null

    fun updateComment(commentRequest: CommentRequest) {
        ment = commentRequest.ment
        updatedAt = LocalDateTime.now()
    }
}