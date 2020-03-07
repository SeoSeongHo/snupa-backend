package com.snupa.service.user

import com.snupa.domain.User
import com.snupa.dto.user.UserReq
import com.snupa.dto.user.UserRes

interface UserService {
    fun getUser(userId: Long): User
    fun updateUser(userReq: UserReq): UserRes
}