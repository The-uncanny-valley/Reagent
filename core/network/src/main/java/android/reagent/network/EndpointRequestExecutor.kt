package android.reagent.network

import android.os.SystemClock
import android.reagent.domain.model.EndpointTestResult
import javax.inject.Inject
import okhttp3.OkHttpClient
import okhttp3.Request


class EndpointRequestExecutor @Inject constructor(
    private val client: OkHttpClient,
) {

    suspend fun execute(
        url: String,
        method: String,
    ): EndpointTestResult {

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


                return EndpointTestResult.HttpResponse(
                    url = url,
                    method = method,
                    startedAt = startedAt,
                    durationMs = duration,
                    statusCode = response.code,
                    statusMessage = response.message,
                    isSuccessful = response.isSuccessful,
                    finalUrl = response.request.url.toString(),
                    body = response.body?.string(),
                    contentType =
                        response.body
                            ?.contentType()
                            ?.toString(),
                    bodySizeBytes =
                        response.body
                            ?.contentLength(),
                    bodyTruncated = false,
                    redirectCount = 0,
                )
            }

        } catch (e: Exception) {

            val duration =
                SystemClock.elapsedRealtime() - startElapsed


            return EndpointTestResult.Failure(
                url = url,
                method = method,
                startedAt = startedAt,
                durationMs = duration,

                errorType = TODO(),
                errorSource = TODO(),
                errorPhase = TODO(),

                errorCode = TODO(),

                userMessage =
                    e.message ?: "Unknown error",

                technicalMessage =
                    e.toString(),

                exceptionClassName =
                    e::class.java.name,

                rootCauseClassName = null,
                rootCauseMessage = null,

                networkAvailable = null,
                networkTransport = null,
                networkValidated = null,

                host = null,
                resolvedIpAddress = null,
            )
        }
    }
}