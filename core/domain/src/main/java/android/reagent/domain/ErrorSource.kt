package android.reagent.domain

enum class ErrorSource {
    DEVICE,
    APPLICATION,
    NETWORK_STACK,
    HTTP_CLIENT,
    REMOTE_SERVER,
    USER,
    UNKNOWN
}