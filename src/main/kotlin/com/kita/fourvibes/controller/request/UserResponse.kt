package com.kita.fourvibes.controller.request

import com.kita.fourvibes.repository.model.User

data class UserResponse(
    val id: Long,
    val nickname: String,
) {
    companion object {
        fun from(user: User): UserResponse {
            return UserResponse(
                id = user.id!!,
                nickname = user.nickname,
            )
        }
    }
}