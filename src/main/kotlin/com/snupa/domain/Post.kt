package com.snupa.domain

import com.snupa.dto.post.PostReq
import com.snupa.util.EnumCollection
import org.springframework.web.multipart.MultipartFile
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
) : EntityAuditing() {
        companion object {
                fun of(
                        postReq: PostReq,
                        user: User,
                        poster: String?
                ): Post {
                        return Post(
                                title = postReq.title,
                                content = postReq.content,
                                startedAt = postReq.date.from,
                                endedAt = postReq.date.to,
                                category = postReq.category,
                                poster = poster,
                                user = user,
                                comments = mutableListOf(),
                                likes = mutableListOf()
                        )
                }
        }

        fun update(postReq: PostReq, poster: String?): Post {
                this.title = postReq.title
                this.content = postReq.content
                this.category = postReq.category
                this.endedAt = postReq.date.to
                this.startedAt = postReq.date.from
                this.poster = poster

                return this
        }

        fun isOwnedBy(userId: Long): Boolean {
                return this.user.id!! == userId
        }
}