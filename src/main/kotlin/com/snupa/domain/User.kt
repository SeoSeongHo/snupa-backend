package com.snupa.domain

import javax.persistence.*

@Entity
@Table(name="snupa_user")
data class User(
        @Id
        override var id: Long? = null,
        var nickname: String,
        var avatar: String? = null,
        var description: String? = null
): EntityAuditing()