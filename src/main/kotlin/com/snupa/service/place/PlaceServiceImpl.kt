package com.snupa.service.place

import com.snupa.dto.place.*
import com.snupa.exception.PlaceNotFoundException
import com.snupa.mapper.PlaceMapper
import com.snupa.repository.PlaceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PlaceServiceImpl(
        @Autowired val placeRepository: PlaceRepository
): PlaceService {
    override fun getPlace(name: String): List<PlaceRes> {
        val places = placeRepository.findByName(name)
        places?: throw PlaceNotFoundException("can not find place : $name")

        return places.map { PlaceMapper.toDto(it) }
    }

    override fun getPlaces(): List<PlaceRes>{
        val places = placeRepository.findAll()
        if(places.isEmpty())
            throw PlaceNotFoundException("can not find any place")

        return places.map { PlaceMapper.toDto(it) }
    }

    override fun updatePlace(placeReq: PlaceReq): PlaceRes {
        val place = placeRepository.save(PlaceMapper.toEntity(placeReq))
        return PlaceMapper.toDto(place)
    }

    //TODO Exception Handling
    override fun deletePlace(id: Long) {
        placeRepository.deleteById(id)
    }
}