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

    fun validate() {
        if (!isValidNickname(this.nickname)) {
            throw IllegalStateException("닉네임은 최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성")
        }

        if (this.password.length < 4) {
            throw IllegalStateException("비밀번호는 최소 4자 이상")
        }

        if (this.password.contains(this.nickname)) {
            throw IllegalStateException("비밀번호는 닉네임과 같은 값이 포함 안됩니다")
        }

        if (this.password != this.passwordcheck) {
            throw IllegalStateException("비밀번호 확인은 비밀번호와 정확하게 일치")
        }
    }

    private fun isValidNickname(nickname: String): Boolean {
        val regex = "^[a-zA-Z0-9]*$".toRegex()
        return nickname.length >= 3 && regex.matches(nickname)
    }
}