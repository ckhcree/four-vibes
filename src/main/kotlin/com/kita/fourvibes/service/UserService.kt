package com.kita.fourvibes.service

import com.kita.fourvibes.controller.request.SignUpRequest
import com.kita.fourvibes.controller.request.UserResponse
import com.kita.fourvibes.repository.UserRepository
import com.kita.fourvibes.repository.model.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    @Transactional
    fun signUp(request: SignUpRequest): UserResponse {

        if (!isValidNickname(request.nickname)) {
            throw IllegalArgumentException("닉네임은 최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성")
        }

        if (userRepository.findByNickname(request.nickname) != null) {
            throw IllegalArgumentException("중복된 닉네임입니다")
        }

        if (request.password.length < 4) {
            throw IllegalArgumentException("비밀번호는 최소 4자 이상")
        }

        if (request.password.contains(request.nickname)) {
            throw IllegalArgumentException("비밀번호는 닉네임과 같은 값이 포함 안됩니다")
        }

        if (request.password != request.passwordcheck) {
            throw IllegalArgumentException("비밀번호 확인은 비밀번호와 정확하게 일치")
        }

        val user = User(
            password = request.password,
            passwordcheck = request.passwordcheck,
            nickname = request.nickname
        )

        userRepository.save(user)

        return request.to(passwordEncoder).let { UserResponse.from(user) }
    }

    // fun signIn(request: SignInRequest): UserResponse {
    //     val user = userRepository.findByNickname(request.nickname)
    //     return UserResponse()
    // }
}

private fun isValidNickname(nickname: String): Boolean {
    val regex = "^[a-zA-Z0-9]*$".toRegex()
    return nickname.length >= 3 && regex.matches(nickname)
}