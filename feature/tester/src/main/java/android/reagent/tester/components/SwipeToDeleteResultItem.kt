package android.reagent.tester.components

import android.reagent.designsystem.ReagentTheme
import android.reagent.tester.model.EndpointResultUiModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import android.reagent.tester.R
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SwipeToDeleteResultItem(
    result: EndpointResultUiModel,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val dismissState = rememberSwipeToDismissBoxState()

    LaunchedEffect(dismissState.currentValue) {
        if (
            dismissState.currentValue ==
            SwipeToDismissBoxValue.EndToStart
        ) {
            onDelete()
        }
    }

    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier,
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = true,
        backgroundContent = {
            DeleteBackground(
                dismissValue = dismissState.targetValue,
            )
        },
        content = {
            EndpointResultCard(
                result = result,
                onDelete = onDelete,
            )
        },
    )
}

@Composable
private fun DeleteBackground(
    dismissValue: SwipeToDismissBoxValue,
) {
    val isDeleting =
        dismissValue == SwipeToDismissBoxValue.EndToStart

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (isDeleting) {
                    Color(0xFFB3261E)
                } else {
                    Color.Transparent
                },
            )
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.CenterEnd,
    ) {
        if (isDeleting) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_bin),
                    contentDescription = "Delete request result",
                    tint = Color.White,
                )
            }
        }
    }
}

@Preview()
@Composable
private fun SwipeToDeleteResultItemPreview() {
    ReagentTheme {
        SwipeToDeleteResultItem(
            result = EndpointResultUiModel(
                id = 1L,
                method = "GET",
                url = "https://api.example.com/v1/status",
                statusText = "200 OK",
                durationMs = 124,
                time = "12:00:00",
                isSuccessful = true,
                body = """
                    {
                      "status": "success"
                    }
                """.trimIndent(),
            ),
            onDelete = {},
        )
    }
}