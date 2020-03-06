package com.snupa.repository

import com.snupa.domain.Place
import org.springframework.data.jpa.repository.JpaRepository

interface PlaceRepository : JpaRepository<Place, Long>{
    fun findByName(name: String): List<Place>?
}