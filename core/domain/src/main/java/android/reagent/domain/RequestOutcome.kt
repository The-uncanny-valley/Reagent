package android.reagent.domain

enum class RequestOutcome {
    HTTP_RESPONSE,
    TRANSPORT_ERROR,
    LOCAL_ERROR,
    CANCELLED
}