package com.snupa.domain

import javax.persistence.*

@Entity
@Table(name="Like")
data class Like (
        @OneToOne
        @JoinColumn(name="user_id", nullable = false)
        var user : User,

        @OneToOne
        @JoinColumn(name="post_id", nullable = false)
        var post : Post
) : EntityAuditing()