package com.snupa.controller

import com.snupa.dto.place.PlaceReq
import com.snupa.dto.place.PlaceRes
import com.snupa.service.place.PlaceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/place")
class PlaceController (
        @Autowired val placeService: PlaceService
){
    @GetMapping()
    fun getPlace(): ResponseEntity<List<PlaceRes>>{
        val places = placeService.getPlaces()
        return ResponseEntity.ok().body(places)
    }

    @GetMapping("/{placeName}")
    fun getPlaceByName(@PathVariable placeName: String): ResponseEntity<List<PlaceRes>>{
        val places = placeService.getPlace(placeName)
        return ResponseEntity.ok().body(places)
    }

    @PostMapping()
    fun updatePlace(@RequestBody placeReq: PlaceReq): ResponseEntity<PlaceRes>{
        val place = placeService.updatePlace(placeReq)
        return ResponseEntity.ok().body(place)
    }

    @DeleteMapping("/{id}")
    fun deletePlace(@PathVariable id: Long): ResponseEntity<String>{
        placeService.deletePlace(id)
        return ResponseEntity.ok().body("success to delete place $id")
    }
}