package com.snupa.service.user

import com.snupa.dto.user.UserReq
import com.snupa.dto.user.UserRes

interface UserService {
    fun getUser(userId: Long): UserRes
    fun updateUser(userReq: UserReq): UserRes
}