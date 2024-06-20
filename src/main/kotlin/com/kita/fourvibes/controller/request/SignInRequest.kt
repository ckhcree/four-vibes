package com.kita.fourvibes.controller.request

data class SignInRequest(
    val password: String,
    val nickname: String,
)