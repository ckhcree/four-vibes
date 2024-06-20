package com.kita.fourvibes.controller.request

import com.kita.fourvibes.repository.model.User
import org.springframework.security.crypto.password.PasswordEncoder

data class SignUpRequest(
    val password: String,
    val passwordcheck: String,
    val nickname: String,
) {
    fun to(encoder: PasswordEncoder): User {
        return User(
            password = encoder.encode(password),
            nickname = nickname,
        )
    }
}