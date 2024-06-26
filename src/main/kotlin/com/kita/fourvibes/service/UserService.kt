package com.kita.fourvibes.service

import com.kita.fourvibes.controller.request.SignInRequest
import com.kita.fourvibes.controller.request.SignInResponse
import com.kita.fourvibes.controller.request.SignUpRequest
import com.kita.fourvibes.controller.request.SignUpResponse
import com.kita.fourvibes.repository.UserRepository
import com.kita.fourvibes.security.JwtProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtProvider: JwtProvider,
) {
    @Transactional
    fun signUp(request: SignUpRequest): SignUpResponse {
        if (userRepository.findByNickname(request.nickname) != null) {
            throw IllegalStateException("중복된 닉네임입니다")
        }

        val user = userRepository.save(request.to(passwordEncoder))

        return SignUpResponse.from(user)
    }

    fun signIn(request: SignInRequest): SignInResponse {
        val user = userRepository.findByNickname(request.nickname)
            ?: throw IllegalStateException("닉네임 또는 패스워드를 확인해주세요")

        if (user.password != request.password) {
            throw IllegalStateException("닉네임 또는 패스워드를 확인해주세요")
        }

        val token = jwtProvider.generateAccessToken(nickname = user.nickname, subject = user.id.toString())

        return SignInResponse(
            id = user.id!!,
            nickname = user.nickname,
            token = token
        )
    }
}