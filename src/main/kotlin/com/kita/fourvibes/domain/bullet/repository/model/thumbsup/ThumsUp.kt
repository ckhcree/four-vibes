package com.kita.fourvibes.domain.bullet.repository.model.thumbsup

import com.kita.fourvibes.domain.bullet.repository.model.Bullet
import com.kita.fourvibes.repository.model.User
import jakarta.persistence.Embeddable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId
import jakarta.persistence.Table
import java.io.Serializable

@Entity
@Table(name = "thumbsups")
class ThumbsUp(
    @EmbeddedId
    val id: ThumbsUpId,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bulletId")
    val bullet: Bullet,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    val user: User,
)

@Embeddable
data class ThumbsUpId(
    val bulletId: Long,
    val userId: Long,
) : Serializable