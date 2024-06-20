package com.kita.fourvibes.controller.request

data class SignUpRequest(
    val password: String,
    val passwordcheck: String,
    val nickname: String,
)