package com.kita.fourvibes.domain.bullet.repository

import com.kita.fourvibes.domain.bullet.repository.model.Bullet
import org.springframework.data.jpa.repository.JpaRepository

interface BulletRepository : JpaRepository<Bullet, Long> {
    fun findAllByOrderByCreatedAtDesc(): List<Bullet>
}