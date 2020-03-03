package com.snupa.dto.user

data class UserReq (
        var id: Long? = null,
        var nickname : String,
        var avatar: String? = null,
        var description: String? = null,
        var emailedVerified: Boolean? = false
)