package com.snupa.dto.post

import com.snupa.util.EnumCollection

data class PostReq(
        var title: String,
        var content: String,
        var placeId: Long,
        var category: EnumCollection.Category,
        var date: EventDate
)
