package com.snupa.service.place

import com.snupa.domain.Place
import com.snupa.dto.place.*

interface PlaceService {
    fun getPlace(name: String): List<PlaceRes>
    fun getPlaces(): List<PlaceRes>
    fun updatePlace(placeReq: PlaceReq): PlaceRes
    fun deletePlace(id: Long)
}