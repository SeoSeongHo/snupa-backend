package com.snupa.domain

import javax.persistence.*

@Entity
@Table(name="setting")
data class Setting (
        @Column(name="email_verified")
        var emailVerified: Boolean? = false,

        @OneToOne
        @JoinColumn(name="user_id", nullable = false)
        var user : User

) : EntityAuditing()