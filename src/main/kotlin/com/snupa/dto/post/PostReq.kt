package com.snupa.dto.post

data class PostReq(
        var title: String,
        var content: String,
        var placeId: Long,
        var date: EventDate
)
