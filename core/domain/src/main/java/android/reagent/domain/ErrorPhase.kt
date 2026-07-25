package android.reagent.domain

enum class ErrorPhase {
    VALIDATION,
    REQUEST_BUILDING,
    DNS,
    TLS_HANDSHAKE,
    REQUEST_HEADERS,
    REQUEST_BODY,
    WAITING_FOR_RESPONSE,
    RESPONSE_HEADERS,
    RESPONSE_BODY,
    REDIRECT,
    UNKNOWN
}