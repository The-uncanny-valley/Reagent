package android.reagent.tester.mapper

import org.json.JSONArray
import org.json.JSONObject

/**
 * Formats this string as pretty-printed JSON when possible.
 *
 * Supports JSON objects and arrays. If the string is empty, is not JSON,
 * or cannot be parsed, the original string is returned unchanged.
 */
internal fun String.formatJsonIfPossible() : String {
    val trimmed = trim()

    if (trimmed.isEmpty()) return this

    return runCatching {
        when {
            trimmed.startsWith("{") -> {
                JSONObject(trimmed).toString(2)
            }

            trimmed.startsWith("[") -> {
                JSONArray(trimmed).toString(2)
            }

            else -> this
        }
    }.getOrElse {
        this
    }
}