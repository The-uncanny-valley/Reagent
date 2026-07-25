package android.reagent.database.entity

import android.reagent.domain.ErrorPhase
import android.reagent.domain.ErrorSource
import android.reagent.domain.NetworkTransport
import android.reagent.domain.RequestErrorType
import android.reagent.domain.RequestOutcome
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "endpoint_test_results",
    indices = [
        Index(value = ["startedAt"]),
        Index(value = ["url"]),
        Index(value = ["outcome"])
    ]
)
data class EndpointTestResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,

    val url: String,
    val method: String,

    val startedAt: Long,
    val durationMs: Long,

    val outcome: RequestOutcome,

    val httpStatusCode: Int?,

    // Not Found
    val httpStatusMessage: String?,
    val isHttpSuccessful: Boolean?,

    val finalUrl: String?,

    val responseBody: String?,
    val responseContentType: String?,
    val responseBodySizeBytes: Long?,
    val responseBodyTruncated: Boolean,

    val errorType: RequestErrorType?,
    val errorSource: ErrorSource?,
    val errorPhase: ErrorPhase?,

    // DNS_RESOLUTION_FAILED
    val errorCode: String?,
    // "Could not resolve the server address"
    val errorUserMessage: String?,
    // "Unable to resolve host example.invalid"
    val errorTechnicalMessage: String?,
    // java.net.UnknownHostException
    val exceptionClassName: String?,

    val rootCauseClassName: String?,
    val rootCauseMessage: String?,

    val redirectCount: Int?,
    val networkAvailable: Boolean?,
    val networkTransport: NetworkTransport?,

    val networkValidated: Boolean?,
    val host: String?,
    val resolvedApiAddress: String?
)