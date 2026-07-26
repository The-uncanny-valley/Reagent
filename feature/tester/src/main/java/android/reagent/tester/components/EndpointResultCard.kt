package android.reagent.tester.components

import android.reagent.designsystem.Nero
import android.reagent.designsystem.Platinum
import android.reagent.designsystem.ReagentTheme
import android.reagent.tester.model.EndpointResultUiModel
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun EndpointResultCard(
    result: EndpointResultUiModel,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Nero),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Platinum)
    ) {

        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            EndpointResultHeader(
                result = result
            )

            Spacer(
                Modifier.height(12.dp)
            )

            Text(
                text = "${result.method} ${result.url}",
                style = MaterialTheme.typography.bodySmall
            )


            if (result.body != null) {

                Spacer(
                    Modifier.height(12.dp)
                )

                ResponseBodyPreview(
                    body = result.body
                )
            }
        }
    }
}

@Composable
fun ResponseBodyPreview(
    body: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(
                min = 100.dp,
                max = 200.dp
            )
            .border(
                1.dp,
                Color.Gray,
                RoundedCornerShape(8.dp)
            )
            .padding(12.dp)
    ) {

        Text(
            text = body,
            fontFamily = FontFamily.Monospace,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview
@Composable
fun EndpointResultCard_Preview() {
    ReagentTheme {
        EndpointResultCard(
            result = EndpointResultUiModel(
                id = 1L,
                method = "GET",
                url = "https://example.com/api/v1/resource",
                statusText = "200 OK",
                durationMs = 124,
                time = "12:00:00",
                isSuccessful = true,
                body = "{\n  \"status\": \"success\",\n  \"data\": {\n    \"id\": 1,\n    \"name\": \"Test Resource\"\n  }\n}"
            ),
            onDelete = {}
        )
    }
}
