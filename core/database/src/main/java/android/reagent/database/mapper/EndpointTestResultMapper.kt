package android.reagent.database.mapper

import android.reagent.database.entity.EndpointTestResultEntity
import android.reagent.domain.ErrorSource
import android.reagent.domain.RequestErrorType
import android.reagent.domain.RequestOutcome
import android.reagent.domain.model.EndpointTestResult


fun EndpointTestResultEntity.toDomain(): EndpointTestResult {
    return when (outcome) {
        RequestOutcome.HTTP_RESPONSE -> EndpointTestResult.HttpResponse(
            id = id,
            url = url,
            method = method,
            startedAt = startedAt,
            durationMs = durationMs,
            statusCode = httpStatusCode ?: 0,
            statusMessage = httpStatusMessage,
            isSuccessful = isHttpSuccessful ?: false,
            finalUrl = finalUrl ?: url,
            body = responseBody,
            contentType = responseContentType,
            bodySizeBytes = responseBodySizeBytes,
            bodyTruncated = responseBodyTruncated,
            redirectCount = redirectCount ?: 0,
        )
        RequestOutcome.CANCELLED -> EndpointTestResult.Cancelled(
            id = id,
            url = url,
            method = method,
            startedAt = startedAt,
            durationMs = durationMs,
        )
        RequestOutcome.TRANSPORT_ERROR, RequestOutcome.LOCAL_ERROR -> EndpointTestResult.Failure(
            id = id,
            url = url,
            method = method,
            startedAt = startedAt,
            durationMs = durationMs,
            errorType = errorType ?: RequestErrorType.UNKNOWN,
            errorSource = errorSource ?: ErrorSource.UNKNOWN,
            errorPhase = errorPhase,
            errorCode = errorCode ?: "UNKNOWN",
            userMessage = errorUserMessage ?: "Unknown error",
            technicalMessage = errorTechnicalMessage,
            exceptionClassName = exceptionClassName,
            rootCauseClassName = rootCauseClassName,
            rootCauseMessage = rootCauseMessage,
            networkAvailable = networkAvailable,
            networkTransport = networkTransport,
            networkValidated = networkValidated,
            host = host,
            resolvedIpAddress = resolvedApiAddress
        )
    }
}

fun EndpointTestResult.toEntity(): EndpointTestResultEntity {
    return when (this) {
        is EndpointTestResult.HttpResponse -> EndpointTestResultEntity(
            id = id,
            url = url,
            method = method,
            startedAt = startedAt,
            durationMs = durationMs,
            outcome = RequestOutcome.HTTP_RESPONSE,
            httpStatusCode = statusCode,
            httpStatusMessage = statusMessage,
            isHttpSuccessful = isSuccessful,
            finalUrl = finalUrl,
            responseBody = body,
            responseContentType = contentType,
            responseBodySizeBytes = bodySizeBytes,
            responseBodyTruncated = bodyTruncated,
            redirectCount = redirectCount,
            errorType = null,
            errorSource = null,
            errorPhase = null,
            errorCode = null,
            errorUserMessage = null,
            errorTechnicalMessage = null,
            exceptionClassName = null,
            rootCauseClassName = null,
            rootCauseMessage = null,
            networkAvailable = null,
            networkTransport = null,
            networkValidated = null,
            host = null,
            resolvedApiAddress = null
        )
        is EndpointTestResult.Failure -> EndpointTestResultEntity(
            id = id,
            url = url,
            method = method,
            startedAt = startedAt,
            durationMs = durationMs,
            outcome = if ((errorSource == ErrorSource.REMOTE_SERVER) || (errorSource == ErrorSource.NETWORK_STACK))
                RequestOutcome.TRANSPORT_ERROR else RequestOutcome.LOCAL_ERROR,
            httpStatusCode = null,
            httpStatusMessage = null,
            isHttpSuccessful = null,
            finalUrl = null,
            responseBody = null,
            responseContentType = null,
            responseBodySizeBytes = null,
            responseBodyTruncated = false,
            redirectCount = null,
            errorType = errorType,
            errorSource = errorSource,
            errorPhase = errorPhase,
            errorCode = errorCode,
            errorUserMessage = userMessage,
            errorTechnicalMessage = technicalMessage,
            exceptionClassName = exceptionClassName,
            rootCauseClassName = rootCauseClassName,
            rootCauseMessage = rootCauseMessage,
            networkAvailable = networkAvailable,
            networkTransport = networkTransport,
            networkValidated = networkValidated,
            host = host,
            resolvedApiAddress = resolvedIpAddress
        )
        is EndpointTestResult.Cancelled -> EndpointTestResultEntity(
            id = id,
            url = url,
            method = method,
            startedAt = startedAt,
            durationMs = durationMs,
            outcome = RequestOutcome.CANCELLED,
            httpStatusCode = null,
            httpStatusMessage = null,
            isHttpSuccessful = null,
            finalUrl = null,
            responseBody = null,
            responseContentType = null,
            responseBodySizeBytes = null,
            responseBodyTruncated = false,
            redirectCount = null,
            errorType = RequestErrorType.CANCELLED,
            errorSource = ErrorSource.USER,
            errorPhase = null,
            errorCode = "CANCELLED",
            errorUserMessage = "Request cancelled",
            errorTechnicalMessage = null,
            exceptionClassName = null,
            rootCauseClassName = null,
            rootCauseMessage = null,
            networkAvailable = null,
            networkTransport = null,
            networkValidated = null,
            host = null,
            resolvedApiAddress = null
        )
    }
}
