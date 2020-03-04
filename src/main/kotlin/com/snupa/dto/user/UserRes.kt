package com.snupa.dto.user

data class UserRes (
        var emailVerified : Boolean? = false,

        var nickname: String? = null,
        var avartar: String? = null,
        var description: String? = null
)