package com.kita.fourvibes.controller.request

import com.kita.fourvibes.repository.model.User

data class SignUpResponse(
    val id: Long,
    val nickname: String,
) {
    companion object {
        fun from(user: User): SignUpResponse {
            return SignUpResponse(
                id = user.id!!,
                nickname = user.nickname,
            )
        }
    }
}