package android.reagent.tester

import android.reagent.domain.repository.EndpointTestRepository
import android.reagent.tester.mapper.toUiModel
import android.reagent.tester.state.EndpointTesterUiState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
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
                .catch { error ->

                    _uiState.update {
                        it.copy(
                            errorMessage = error.message ?: "Failed to load history"
                        )
                    }
                }
                .collect { results ->
                    _uiState.update {
                        it.copy(
                            history = results.map { result ->
                                result.toUiModel()
                            },
                            recentEndpoints = results
                                .distinctBy { result ->
                                    result.url
                                }
                                .take(5)
                                .map { result ->
                                    result.url
                                }
                        )
                    }
                }
        }
    }

    fun updateUrl(url: String) {
        _uiState.update {
            it.copy(url = url)
        }
    }

    fun runTest() {
        val currentState = _uiState.value

        if (currentState.isTesting) return

        val url = currentState.url.trim()

        if (url.isBlank()) {
            _uiState.update { state ->
                state.copy(errorMessage = "Enter a valid url")
            }

            return
        }

         viewModelScope.launch {

             _uiState.update {
                 it.copy(
                     isTesting = true
                 )
             }

             try {
                 repository.testEndpoint(
                     url = url,
                     method = "GET"
                 )
             } catch (e: CancellationException) {
                 throw e
             } catch (e: Exception) {
                 _uiState.update {
                     it.copy(
                         errorMessage = e.message ?: "Unknown error"
                     )
                 }
             } finally {
                 _uiState.update {
                     it.copy(
                         isTesting = false
                     )
                 }
             }
         }
    }

    fun runRecentEndpoint(url: String) {
        _uiState.update {
            it.copy(url = url)
        }

        runTest()
    }

    fun deleteAll() {
        viewModelScope.launch {
            try {
                repository.deleteAllResults()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(
                        errorMessage = e.message ?: "Failed to clear history"
                    )
                }
            }
        }
    }

    fun deleteResult(id: Long) {
        viewModelScope.launch {
            try {
                repository.deleteResult(id)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(
                        errorMessage = e.message ?: "Failed to delete the result"
                    )
                }
            }
        }
    }

    fun dismissError() {
        _uiState.update {
            it.copy(errorMessage = null)
        }
    }
}