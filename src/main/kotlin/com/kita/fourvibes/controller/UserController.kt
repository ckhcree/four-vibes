package com.kita.fourvibes.controller

import com.kita.fourvibes.controller.request.SignInRequest
import com.kita.fourvibes.controller.request.SignInResponse
import com.kita.fourvibes.controller.request.SignUpRequest
import com.kita.fourvibes.controller.request.SignUpResponse
import com.kita.fourvibes.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userService: UserService,
) {
    @PostMapping("/sign-up")
    fun signUp(
        @RequestBody request: SignUpRequest
    ): ResponseEntity<SignUpResponse> {
        val passUser = userService.signUp(request)

        request.validate()

        return ResponseEntity.status(HttpStatus.CREATED).body(passUser)
    }

    @PostMapping("/sign-in")
    fun signIn(
        @RequestBody request: SignInRequest
    ): ResponseEntity<SignInResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.signIn(request))
    }
}