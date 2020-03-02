package com.snupa.domain

import com.snupa.domain.EntityAuditing
import javax.persistence.*

@Entity
@Table(name="snupa_place")
data class Place (
    @Column(name="name")
    var name : String? = null,

    @Column(name="address")
    var address : String? = null

) : EntityAuditing()