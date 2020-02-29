package com.snupa.util

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*


// TODO 성능이 안좋다면, static 으로 빼는 형태로 생각해보기
class LocalDateTimeConverter : DynamoDBTypeConverter<Date, LocalDateTime> {
    override fun convert(source : LocalDateTime) : Date {
        return Date.from(source.toInstant(ZoneOffset.UTC))
    }

    override fun unconvert(source : Date): LocalDateTime {
        return source.toInstant().atZone(TimeZone.getDefault().toZoneId()).toLocalDateTime();
    }
}