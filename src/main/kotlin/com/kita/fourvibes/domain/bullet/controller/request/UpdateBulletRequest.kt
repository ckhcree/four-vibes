package com.kita.fourvibes.domain.bullet.controller.request

data class UpdateBulletRequest(
    val nickname: String,
    val title: String,
    val memo: String,
    val token: String,
)