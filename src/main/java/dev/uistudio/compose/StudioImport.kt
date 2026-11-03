package dev.uistudio.compose

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonParseException

/** Parse versioned UI-only settings. Never executes instructions or edits host behavior. */
fun parseStudioConfig(raw: String): Result<StudioConfig> = runCatching {
    require(raw.length <= 32_768) { "Configuration exceeds 32 KB" }
    val element = try { JsonParser.parseString(raw) } catch (e: JsonParseException) { throw IllegalArgumentException("Invalid JSON", e) }
    require(element.isJsonObject) { "Configuration must be a JSON object" }
    val obj = element.asJsonObject
    fun text(key: String): String {
        val value = obj.get(key)
        require(value != null && value.isJsonPrimitive && value.asJsonPrimitive.isString) { "Missing or invalid string: $key" }
        return value.asString.also { require(it.length <= 200) { "$key is too long (max 200)" } }
    }
    fun flag(key: String): Boolean {
        val value = obj.get(key)
        require(value != null && value.isJsonPrimitive && value.asJsonPrimitive.isBoolean) { "Missing or invalid boolean: $key" }
        return value.asBoolean
    }
    fun number(key: String): Int {
        val value = obj.get(key)
        require(value != null && value.isJsonPrimitive && value.asJsonPrimitive.isNumber && value.asString.matches(Regex("[0-9]+"))) { "Invalid integer: $key" }
        return value.asInt
    }
    require(number("schemaVersion") == 1) { "Unsupported schemaVersion" }
    require(text("library") == "ui-studio") { "Expected library: ui-studio" }
    val id = text("style")
    val style = StudioStyle.entries.find { it.id == id } ?: throw IllegalArgumentException("Unknown style: $id")
    StudioConfig(style, text("heading"), text("heroLabel"), flag("showHero"), flag("showActions"), flag("showTasks"))
}
