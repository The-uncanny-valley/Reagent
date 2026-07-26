package android.reagent.tester.state

import android.reagent.domain.model.EndpointTestResult
import android.reagent.tester.model.EndpointResultUiModel

data class EndpointTesterUiState(
    val url: String = "",
    val history: List<EndpointResultUiModel> = emptyList(),
    val recentEndpoints: List<String> = emptyList(),
    val isTesting: Boolean = false,
    val errorMessage: String? = null
)
