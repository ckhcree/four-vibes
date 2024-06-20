package com.kita.fourvibes.repository.model

import com.kita.fourvibes.controller.request.SignUpRequest
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.security.crypto.password.PasswordEncoder

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null,
    private var password: String = "",
    private var passwordcheck: String = "",
    private var nickname: String = "",
) {

    companion object {
        fun from(request: SignUpRequest, encoder: PasswordEncoder): User {
            val password = encoder.encode(request.password)
            val passwordcheck = encoder.encode(request.passwordcheck)
            val nickname = request.nickname

            return User(
                password = password,
                passwordcheck = passwordcheck,
                nickname = nickname,
            )
        }
    }

    fun isCorrectPassword(password: String): Boolean {
        return this.password == password && this.passwordcheck == password
    }
}