package com.commonpepper.photosen.network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.IllegalStateException
import java.lang.reflect.Type

internal class BooleanTypeAdapter : JsonDeserializer<Boolean> {
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type,
                             context: JsonDeserializationContext): Boolean {
        try {
            val code = json.asInt
            return code != 0
        } catch (e : NumberFormatException) {
            return json.asBoolean
        }
    }
}