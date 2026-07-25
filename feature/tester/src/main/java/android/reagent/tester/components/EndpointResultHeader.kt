package android.reagent.tester.components

import android.reagent.tester.model.EndpointResultUiModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun EndpointResultHeader(
    result: EndpointResultUiModel
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        ResultStatusBadge(
            success = result.isSuccessful
        )

        Spacer(
            Modifier.width(16.dp)
        )

        Text(
            text = result.statusText,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            Modifier.weight(1f)
        )

        Text(
            "${result.durationMs} ms"
        )

        Spacer(
            Modifier.width(12.dp)
        )

        VerticalPipe(
            color = Color.Black
        )

        Spacer(
            Modifier.width(12.dp)
        )

        Text(
            result.time
        )
    }
}

@Composable
private fun VerticalPipe(
    color: Color
) {
    Box(
        modifier = Modifier
            .height(20.dp)
            .width(1.dp)
            .background(color)
    )
}

@Preview(showBackground = true)
@Composable
fun EndpointResultHeader_Preview() {
    EndpointResultHeader(
        result = EndpointResultUiModel(
            id = 1L,
            method = "GET",
            url = "https://example.com/api/v1/resource",
            statusText = "200 OK",
            durationMs = 124,
            time = "12:00:00",
            isSuccessful = true,
            body = null
        )
    )
}