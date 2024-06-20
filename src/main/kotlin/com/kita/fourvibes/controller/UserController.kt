package com.kita.fourvibes.controller

import com.kita.fourvibes.controller.request.SignUpRequest
import com.kita.fourvibes.controller.request.UserResponse
import com.kita.fourvibes.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userService: UserService,
) {
    @Transactional
    @PostMapping("/sign-up")
    fun signUp(
        @RequestBody request: SignUpRequest
    ): ResponseEntity<UserResponse> {
        val passUser = userService.signUp(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(passUser)
    }
}