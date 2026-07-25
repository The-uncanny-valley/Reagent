package android.reagent.domain.model

import android.reagent.domain.ErrorPhase
import android.reagent.domain.ErrorSource
import android.reagent.domain.NetworkTransport
import android.reagent.domain.RequestErrorType

sealed interface EndpointTestResult {

    val url: String
    val method: String
    val startedAt: Long
    val durationMs: Long

    data class HttpResponse(
        override val url: String,
        override val method: String,
        override val startedAt: Long,
        override val durationMs: Long,
        val statusCode: Int,
        val statusMessage: String?,
        val isSuccessful: Boolean,
        val finalUrl: String,
        val body: String?,
        val contentType: String?,
        val bodySizeBytes: Long?,
        val bodyTruncated: Boolean,
        val redirectCount: Int,
    ) : EndpointTestResult

    data class Failure(
        override val url: String,
        override val method: String,
        override val startedAt: Long,
        override val durationMs: Long,
        val errorType: RequestErrorType,
        val errorSource: ErrorSource,
        val errorPhase: ErrorPhase?,
        val errorCode: String,
        val userMessage: String,
        val technicalMessage: String?,
        val exceptionClassName: String?,
        val rootCauseClassName: String?,
        val rootCauseMessage: String?,
        val networkAvailable: Boolean?,
        val networkTransport: NetworkTransport?,
        val networkValidated: Boolean?,
        val host: String?,
        val resolvedIpAddress: String?,
    ) : EndpointTestResult

    data class Cancelled(
        override val url: String,
        override val method: String,
        override val startedAt: Long,
        override val durationMs: Long,
    ) : EndpointTestResult
}