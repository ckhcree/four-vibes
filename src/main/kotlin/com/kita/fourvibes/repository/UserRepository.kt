package com.kita.fourvibes.repository

import com.kita.fourvibes.repository.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByPasswordAndNickname(password: String, nickname: String): User?
}