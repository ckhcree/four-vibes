package com.kita.fourvibes.service

import com.kita.fourvibes.controller.request.SignInRequest
import com.kita.fourvibes.controller.request.SignUpRequest
import com.kita.fourvibes.controller.request.UserResponse
import com.kita.fourvibes.repository.UserRepository
import com.kita.fourvibes.repository.model.User
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun signUp(signUpRequest: SignUpRequest): UserResponse {
        val user = User(
            password = signUpRequest.password,
            passwordcheck = signUpRequest.passwordcheck,
            nickname = signUpRequest.nickname
        )
        userRepository.save(user)

        return UserResponse()
    }

    fun signIn(signInRequest: SignInRequest): UserResponse {
        val user = userRepository.findByPasswordAndNickname(signInRequest.password, signInRequest.nickname)
        return UserResponse()
    }
}