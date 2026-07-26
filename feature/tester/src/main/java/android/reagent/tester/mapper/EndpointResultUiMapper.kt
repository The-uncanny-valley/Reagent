package android.reagent.tester.mapper

import android.reagent.domain.model.EndpointTestResult
import android.reagent.tester.model.EndpointResultUiModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun EndpointTestResult.toUiModel(): EndpointResultUiModel {

    return when (this) {

        is EndpointTestResult.HttpResponse -> {

            EndpointResultUiModel(
                id = hashCode().toLong(),

                method = method,

                url = url,

                statusText =
                    if (statusMessage != null) {
                        "$statusCode $statusMessage"
                    } else {
                        statusCode.toString()
                    },

                durationMs = durationMs,

                time = formatTime(startedAt),

                isSuccessful = isSuccessful,

                body = body,
            )
        }


        is EndpointTestResult.Failure -> {

            EndpointResultUiModel(
                id = hashCode().toLong(),

                method = method,

                url = url,

                statusText = errorType.name,

                durationMs = durationMs,

                time = formatTime(startedAt),

                isSuccessful = false,

                body = technicalMessage,
            )
        }


        is EndpointTestResult.Cancelled -> {

            EndpointResultUiModel(
                id = hashCode().toLong(),

                method = method,

                url = url,

                statusText = "Cancelled",

                durationMs = durationMs,

                time = formatTime(startedAt),

                isSuccessful = false,

                body = null,
            )
        }
    }
}


private fun formatTime(
    timestamp: Long,
): String {

    return SimpleDateFormat(
        "HH:mm:ss",
        Locale.getDefault(),
    ).format(
        Date(timestamp)
    )
}