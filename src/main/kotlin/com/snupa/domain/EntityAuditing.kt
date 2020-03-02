package com.snupa.domain

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@EntityListeners(value=[AuditingEntityListener::class])
@MappedSuperclass
abstract class EntityAuditing {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    var id: Long? = null
        private set

    @CreationTimestamp
    @Column(name="created_at", nullable = false, updatable = false)
    lateinit var createdAd : LocalDateTime
        private set

    @UpdateTimestamp
    @Column(name="updated_at", nullable = false)
    lateinit var updatedAt : LocalDateTime
        private set

    @PrePersist
    fun onPrePersist(){
        this.createdAd = LocalDateTime.now()
        this.updatedAt = this.createdAd
    }

    @PreUpdate
    fun opPreUpdate(){
        this.updatedAt = LocalDateTime.now()
    }
}