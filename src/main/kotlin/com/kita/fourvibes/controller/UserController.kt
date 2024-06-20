package com.kita.fourvibes.controller

import com.kita.fourvibes.controller.request.SignInRequest
import com.kita.fourvibes.controller.request.SignUpRequest
import com.kita.fourvibes.controller.request.UserResponse
import com.kita.fourvibes.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    val userService: UserService,
) {
    @PostMapping("/sign-up")
    fun signUp(
        @RequestBody request: SignUpRequest
    ): ResponseEntity<UserResponse> {
        // if 닉네임이 최소 3자 이상 아니면, 그리고  닉네임이 알파벳 대소문자,숫자 아니면 throw
        // if 닉네임이 이미 있는 닉네임이면 throw

        // if 비밀번호 최소 4자 이상 아니면, 그리고 비밀번호에 닉네임이 포함되면 throw
        // if 비밀번호확인이 비밀번호랑 다르면 throw

        TODO()
    }
}

@Transactional
@PostMapping("/sign-in")
fun signIn(
    @RequestBody request: SignInRequest
): ResponseEntity<UserResponse> {
    return TODO()
}