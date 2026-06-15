package com.example.trustline.data

import com.google.gson.*
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class LocalDateTimeAdapter : JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    private val fallbackFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    override fun serialize(
        src: LocalDateTime?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return if (src == null) JsonNull.INSTANCE else JsonPrimitive(formatter.format(src))
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDateTime? {
        val dateString = json?.asString ?: return null
        if (dateString.isEmpty()) return null

        return try {
            LocalDateTime.parse(dateString, formatter)
        } catch (e: DateTimeParseException) {
            try {
                LocalDateTime.parse(dateString, fallbackFormatter)
            } catch (e2: DateTimeParseException) {
                null
            }
        }
    }
}
