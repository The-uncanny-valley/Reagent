package android.reagent.tester

import android.reagent.domain.repository.EndpointTestRepository
import android.reagent.tester.state.EndpointTesterUiState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EndpointTesterViewModel @Inject constructor(
    private val repository: EndpointTestRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<EndpointTesterUiState>(
        EndpointTesterUiState()
    )

    val uiState: StateFlow<EndpointTesterUiState> = _uiState

    init {
        observeHistory()
    }

    private fun observeHistory() {
        viewModelScope.launch {

            repository.observeResults()
                .collect { results ->

                    _uiState.update {
                        it.copy(
                            history = results
                        )
                    }
                }
        }
    }

    fun runTest(url: String) {
         viewModelScope.launch {

             _uiState.update {
                 it.copy(
                     isTesting = true
                 )
             }

             repository.testEndpoint(
                 url = url,
                 method = "GET"
             )

             _uiState.update {
                 it.copy(
                     isTesting = false
                 )
             }
         }
    }

    fun deleteAll() {
        viewModelScope.launch {
            try {
                repository.deleteAllResults()
            } catch (e: Exception) {

            }
        }
    }

}