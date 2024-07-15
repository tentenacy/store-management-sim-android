package com.tenutz.storemngsim.utils.converter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.utils.constant.DateConstant.DATE_FORMATS
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class DateDeserializer : JsonDeserializer<Date?> {

    @Throws(JsonParseException::class)
    override fun deserialize(
        jsonElement: JsonElement, typeOF: Type?,
        context: JsonDeserializationContext?
    ): Date? {
        for (format: String? in DATE_FORMATS) {
            try {
                return SimpleDateFormat(format, Locale.KOREA).parse(jsonElement.asString)
            } catch (e: ParseException) {
            }
        }
        Logger.e(
            JsonParseException(
                "Unparseable date: \"" + jsonElement.asString
                    .toString() + "\". Supported formats: " + DATE_FORMATS.contentToString()
            ).toString()
        )
        return null
    }
}