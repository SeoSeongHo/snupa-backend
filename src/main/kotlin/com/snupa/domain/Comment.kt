package com.snupa.domain

import javax.persistence.*

@Entity
@Table(name="comment")
data class Comment (
        @Column(name="content")
        var content : String? = null,

        @ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="user_id", nullable = false)
        var user : User,

        @OneToMany(fetch=FetchType.LAZY)
        @JoinColumn(name="post_id", nullable = false)
        var post : Post
) : EntityAuditing()