package com.snupa.domain

import javax.persistence.*

@Entity
@Table(name="snupa_like")
data class Like (
        @ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="user_id", nullable = false)
        var user : User,

        @ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="post_id", nullable = false)
        var post : Post
) : EntityAuditing()