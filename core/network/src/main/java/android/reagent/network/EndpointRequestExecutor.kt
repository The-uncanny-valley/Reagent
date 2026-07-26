package android.reagent.network

import android.os.SystemClock
import android.reagent.domain.ErrorPhase
import android.reagent.domain.ErrorSource
import android.reagent.domain.RequestErrorType
import android.reagent.domain.model.EndpointTestResult
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.net.ssl.SSLException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class EndpointRequestExecutor @Inject constructor(
    private val client: OkHttpClient,
) {

    suspend fun execute(
        url: String,
        method: String,
    ): EndpointTestResult = withContext(Dispatchers.IO) {

        val startedAt = System.currentTimeMillis()
        val startElapsed = SystemClock.elapsedRealtime()

        try {

            val request = Request.Builder()
                .url(url)
                .method(
                    method,
                    null,
                )
                .build()


            client.newCall(request).execute().use { response ->

                val duration =
                    SystemClock.elapsedRealtime() - startElapsed


                return@withContext EndpointTestResult.HttpResponse(
                    url = url,
                    method = method,
                    startedAt = startedAt,
                    durationMs = duration,
                    statusCode = response.code,
                    statusMessage = response.message,
                    isSuccessful = response.isSuccessful,
                    finalUrl = response.request.url.toString(),
                    body = response.body.string(),
                    contentType =
                        response.body
                            .contentType()
                            ?.toString(),
                    bodySizeBytes =
                        response.body
                            .contentLength(),
                    bodyTruncated = false,
                    redirectCount = 0,
                )
            }

        } catch (e: CancellationException) {
            val duration =
                SystemClock.elapsedRealtime() - startElapsed

            return@withContext EndpointTestResult.Cancelled(
                url = url,
                method = method,
                startedAt = startedAt,
                durationMs = duration,
            )
        } catch (e: Exception) {

            val duration =
                SystemClock.elapsedRealtime() - startElapsed

            val errorDetails = mapError(e)
            val rootCause = getRootCause(e)

            return@withContext EndpointTestResult.Failure(
                url = url,
                method = method,
                startedAt = startedAt,
                durationMs = duration,

                errorType = errorDetails.type,
                errorSource = errorDetails.source,
                errorPhase = errorDetails.phase,

                errorCode = errorDetails.code,

                userMessage =
                    e.message ?: "Unknown error",

                technicalMessage =
                    e.toString(),

                exceptionClassName =
                    e::class.java.name,

                rootCauseClassName = rootCause::class.java.name,
                rootCauseMessage = rootCause.message,

                networkAvailable = null,
                networkTransport = null,
                networkValidated = null,

                host = null,
                resolvedIpAddress = null,
            )
        }
    }

    private fun mapError(e: Throwable): ErrorDetails {
        return when (e) {
            is UnknownHostException -> ErrorDetails(
                type = RequestErrorType.DNS_FAILURE,
                source = ErrorSource.NETWORK_STACK,
                phase = ErrorPhase.DNS,
                code = "DNS_FAILURE"
            )
            is SocketTimeoutException -> ErrorDetails(
                type = RequestErrorType.CONNECT_TIMEOUT,
                source = ErrorSource.NETWORK_STACK,
                phase = ErrorPhase.WAITING_FOR_RESPONSE,
                code = "TIMEOUT"
            )
            is ConnectException -> ErrorDetails(
                type = RequestErrorType.CONNECTION_FAILED,
                source = ErrorSource.NETWORK_STACK,
                phase = null,
                code = "CONNECTION_FAILED"
            )
            is SSLException -> ErrorDetails(
                type = RequestErrorType.TLS_HANDSHAKE_FAILED,
                source = ErrorSource.NETWORK_STACK,
                phase = ErrorPhase.TLS_HANDSHAKE,
                code = "SSL_ERROR"
            )
            is IOException -> ErrorDetails(
                type = RequestErrorType.IO_ERROR,
                source = ErrorSource.NETWORK_STACK,
                phase = null,
                code = "IO_ERROR"
            )
            is IllegalArgumentException -> ErrorDetails(
                type = RequestErrorType.INVALID_URL,
                source = ErrorSource.USER,
                phase = ErrorPhase.VALIDATION,
                code = "INVALID_URL"
            )
            else -> ErrorDetails(
                type = RequestErrorType.UNKNOWN,
                source = ErrorSource.UNKNOWN,
                phase = null,
                code = "UNKNOWN"
            )
        }
    }

    private fun getRootCause(e: Throwable): Throwable {
        var rootCause = e
        while (rootCause.cause != null && rootCause.cause != rootCause) {
            rootCause = rootCause.cause!!
        }
        return rootCause
    }

    private data class ErrorDetails(
        val type: RequestErrorType,
        val source: ErrorSource,
        val phase: ErrorPhase?,
        val code: String
    )
}
