package com.kita.fourvibes.domain.bullet.controller.request

data class UpdateBulletRequest(
    val title: String,
    val memo: String,
    val token: String,
)