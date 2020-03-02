package com.snupa.domain

import com.snupa.util.EnumCollection
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name="snupa_post")
data class Post (
        @Column(name="title", nullable = false)
        var title : String,

        @Column(name="content")
        var content : String? = null,

        // serialized string
        @Column(name="poster")
        var poster : String? = null,

        @Column(name="started_at", nullable = false)
        var startedAt : LocalDateTime,

        @Column(name="category")
        @Enumerated(EnumType.STRING)
        var category : EnumCollection.Category,

        @Column(name="ended_at", nullable = false)
        var endedAt : LocalDateTime,

        @ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="user_id", nullable = false)
        var user : User,

        @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
        var comments : MutableList<Comment>,

        @OneToMany(fetch=FetchType.LAZY, mappedBy = "post")
        var likes : MutableList<Like>
) : EntityAuditing()