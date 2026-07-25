package android.reagent.tester.model

data class EndpointResultUiModel(
    val id: Long,

    val method: String,
    val url: String,

    val statusText: String,

    val durationMs: Long,

    val time: String,

    val isSuccessful: Boolean,

    val body: String?,
)