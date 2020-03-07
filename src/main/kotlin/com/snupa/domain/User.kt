package com.snupa.domain

import javax.persistence.*

@Entity
@Table(name="snupa_user")
data class User(
        @Id
        override var id: Long? = null,
        @Column(name="nickname", nullable = false)
        var nickname: String,
        @Column(name="avatar")
        var avatar: String? = null,
        @Column(name="description")
        var description: String? = null,
        @Column(name="emailed_verified")
        var emailedVerified: Boolean? = false
): EntityAuditing()