package com.snupa.mapper

import com.snupa.domain.Place
import com.snupa.dto.place.*

class PlaceMapper {
    companion object{
        fun toDto(place: Place): PlaceRes{
            return PlaceRes(
                    place.id,
                    place.name,
                    place.address
            )
        }

        fun toEntity(placeReq: PlaceReq): Place{
            return Place(
                    placeReq.name,
                    placeReq.address
            )
        }
    }
}