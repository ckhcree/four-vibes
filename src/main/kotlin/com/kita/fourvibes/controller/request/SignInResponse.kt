package com.kita.fourvibes.controller.request

data class SignInResponse(
    val id: Long,
    val nickname: String,
    val token: String,
)