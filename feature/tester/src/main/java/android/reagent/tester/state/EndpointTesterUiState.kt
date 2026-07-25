package android.reagent.tester.state

import android.reagent.domain.model.EndpointTestResult

data class EndpointTesterUiState(
    val history: List<EndpointTestResult> = emptyList(),

    val isTesting: Boolean = false,

    val errorMessage: String? = null
)
