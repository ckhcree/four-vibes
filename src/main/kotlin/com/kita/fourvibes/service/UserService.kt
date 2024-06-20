package com.kita.fourvibes.service

import com.kita.fourvibes.controller.request.SignInRequest
import com.kita.fourvibes.controller.request.SignUpRequest
import com.kita.fourvibes.repository.UserRepository
import com.kita.fourvibes.repository.model.User
import org.springframework.stereotype.Service

@Service
class UserService(
    private val memberRepository: UserRepository
) {
    fun signUp(signUpRequest: SignUpRequest): Boolean {
        val member = User(
            password = signUpRequest.password,
            passwordcheck = signUpRequest.passwordcheck,
            nickname = signUpRequest.nickname
        )
        memberRepository.save(member)
        return true
    }

    fun signIn(signInRequest: SignInRequest): Boolean {
        val member = User(
            password = signInRequest.password,
            nickname = signInRequest.nickname
        )
        return true
    }
}