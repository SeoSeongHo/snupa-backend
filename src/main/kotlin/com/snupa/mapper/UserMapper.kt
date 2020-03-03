package com.snupa.mapper

import com.snupa.domain.User
import com.snupa.dto.user.UserReq
import com.snupa.dto.user.UserRes

class UserMapper {
    companion object{
        fun toDto(user : User): UserRes{
            return UserRes(
                    user.emailedVerified,
                    user.nickname,
                    user.avatar,
                    user.description
            )
        }

        fun toEntity(userReq: UserReq): User{
            return User(
                    userReq.id,
                    userReq.nickname,
                    userReq.avatar,
                    userReq.description,
                    userReq.emailedVerified
            )
        }
    }
}