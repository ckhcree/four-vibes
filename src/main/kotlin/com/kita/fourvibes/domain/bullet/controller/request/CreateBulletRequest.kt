package com.kita.fourvibes.domain.bullet.controller.request

import jakarta.validation.constraints.Size

data class CreateBulletRequest(
    val nickname: String,

    @field:Size(min = 1, max = 500, message = "500자 까지 입력 가능")
    val title: String,

    @field:Size(min = 1, max = 5000, message = "5000자 까지 입력 가능")
    val memo: String,

    val token: String,
)