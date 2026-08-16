package android.reagent.tester.mapper

import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal fun String.formatResponseIfPossible(
    contentType: String?
): String {
    val trimmed = trim()

    if (trimmed.isEmpty()) return this

    return when {
        contentType.isJson() -> formatJsonIfPossible()
        contentType.isHtml() -> formatHtmlIfPossible()
        contentType.isXml() -> formatXmlIfPossible()
        else -> this
    }
}

/**
 * Formats this string as pretty-printed JSON when possible.
 *
 * Supports JSON objects and arrays. If the string is empty, is not JSON,
 * or cannot be parsed, the original string is returned unchanged.
 */
private fun String.formatJsonIfPossible(): String {
    val trimmed = trim()

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

/**
 * Formats this string as pretty-printed HTML when possible.
 */
private fun String.formatHtmlIfPossible(): String {
    return runCatching {
        val document = Jsoup.parse(this)

        document
            .outputSettings()
            .prettyPrint(true)
            .indentAmount(2)

        document.outerHtml()
    }.getOrElse {
        this
    }
}

/**
 * Formats this string as pretty-printed XML when possible.
 */
private fun String.formatXmlIfPossible(): String {
    return runCatching {
        val document = Jsoup.parse(
            this,
            "",
            Parser.xmlParser()
        )

        document
            .outputSettings()
            .prettyPrint(true)
            .indentAmount(2)

        document.outerHtml()
    }.getOrElse {
        this
    }
}

private fun String?.isJson(): Boolean {
    return this?.contains(
        other = "json",
        ignoreCase = true
    ) == true
}

private fun String?.isHtml(): Boolean {
    return this?.contains(
        other = "html",
        ignoreCase = true
    ) == true
}

private fun String?.isXml(): Boolean {
    return this?.contains(
        other = "xml",
        ignoreCase = true
    ) == true
}

internal fun formatTime(
    timestamp: Long,
): String {

    return SimpleDateFormat(
        "HH:mm:ss",
        Locale.getDefault(),
    ).format(
        Date(timestamp)
    )
}
