package com.snupa.domain.user

import javax.persistence.*

@Entity
data class User(
        @Id
        var id: Long? = null,
        var nickname: String,
        var avatar: String? = null,
        var description: String? = null
) {
    constructor(): this(
            nickname = "",
            avatar = "",
            description = ""
    )
}