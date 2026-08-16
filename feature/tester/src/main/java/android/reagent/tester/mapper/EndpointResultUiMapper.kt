package android.reagent.tester.mapper

import android.reagent.domain.model.EndpointTestResult
import android.reagent.tester.model.EndpointResultUiModel

fun EndpointTestResult.toUiModel(): EndpointResultUiModel {

    return when (this) {
        is EndpointTestResult.HttpResponse -> {
            EndpointResultUiModel(
                id = id,
                method = method,
                url = url,
                statusText = if (statusMessage != null) {
                    "$statusCode $statusMessage"
                } else {
                    statusCode.toString()
                },
                durationMs = durationMs,
                time = formatTime(startedAt),
                isSuccessful = isSuccessful,
                body = body?.formatResponseIfPossible(
                    contentType = contentType
                )
            )
        }


        is EndpointTestResult.Failure -> {
            EndpointResultUiModel(
                id = id,
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
                id = id,
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
