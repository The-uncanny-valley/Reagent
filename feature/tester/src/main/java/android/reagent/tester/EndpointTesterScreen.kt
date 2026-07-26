package android.reagent.tester

import android.reagent.designsystem.Nero
import android.reagent.designsystem.ReagentTheme
import android.reagent.tester.components.EndpointInputField
import android.reagent.tester.components.EndpointTesterHeader
import android.reagent.tester.components.RecentEndpointsRow
import android.reagent.tester.components.RunRequestButton
import android.reagent.tester.components.SwipeToDeleteResultItem
import android.reagent.tester.model.EndpointResultUiModel
import android.reagent.tester.state.EndpointTesterUiState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun EndpointTesterScreen(
    state: EndpointTesterUiState,
    onUrlChange: (String) -> Unit,
    onRunRequest: () -> Unit,
    onDeleteResult: (Long) -> Unit,
    onPasteFromClipboard: () -> Unit
) {
    val listState = rememberLazyListState()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(state.history.firstOrNull()?.id) {
        if (state.history.isNotEmpty()) {
            listState.animateScrollToItem(0)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Nero
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EndpointTesterHeader()

            Spacer(Modifier.height(18.dp))

            Text(
                text = "Test any endpoint and see the response."
            )

            Spacer(Modifier.height(24.dp))

            EndpointInputField(
                value = state.url,
                onValueChange = onUrlChange,
                onPasteFromClipboard = onPasteFromClipboard
            )

            Spacer(Modifier.height(18.dp))

            RunRequestButton(
                enabled = !state.isTesting,
                onClick = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    onRunRequest()
                },
            )

            Spacer(Modifier.height(20.dp))

            RecentEndpointsRow(
                endpoints = state.recentEndpoints,
            )

            Spacer(Modifier.height(16.dp))

            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = state.history,
                    key = { it.id }
                ) { result ->

                    SwipeToDeleteResultItem(
                        result = result,
                        onDelete = {
                            onDeleteResult(result.id)
                        },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EndpointTesterScreenPreview() {
    ReagentTheme {
        EndpointTesterScreen(
            state = EndpointTesterUiState(
                url = "https://api.example.com/v1/status",
                recentEndpoints = listOf(
                    "https://api.example.com/v1",
                    "https://api.test.org/auth",
                    "localhost:8080/debug"
                ),
                history = listOf(
                    EndpointResultUiModel(
                        id = 1,
                        method = "GET",
                        url = "https://api.example.com/v1/users",
                        statusText = "200 OK",
                        durationMs = 150,
                        time = "10:30 AM",
                        isSuccessful = true,
                        body = "{\"status\":\"success\",\"data\":[]}"
                    ),
                    EndpointResultUiModel(
                        id = 2,
                        method = "POST",
                        url = "https://api.example.com/v1/login",
                        statusText = "401 Unauthorized",
                        durationMs = 80,
                        time = "10:35 AM",
                        isSuccessful = false,
                        body = "{\"error\":\"invalid_credentials\"}"
                    )
                )
            ),
            onUrlChange = {},
            onRunRequest = {},
            onDeleteResult = {},
            onPasteFromClipboard = {}
        )
    }
}
