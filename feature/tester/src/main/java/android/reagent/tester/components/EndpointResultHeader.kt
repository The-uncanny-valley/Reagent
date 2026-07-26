package android.reagent.tester.components

import android.reagent.designsystem.Platinum
import android.reagent.tester.model.EndpointResultUiModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun EndpointResultHeader(
    result: EndpointResultUiModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ResultStatusBadge(
            success = result.isSuccessful,
        )

        Spacer(Modifier.width(16.dp))

        Text(
            text = result.statusText,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        Spacer(Modifier.width(12.dp))

        Text(
            text = "${result.durationMs} ms",
            maxLines = 1,
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(Modifier.width(12.dp))

        VerticalPipe(
            color = Platinum,
        )

        Spacer(Modifier.width(12.dp))

        Text(
            text = result.time,
            maxLines = 1,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun VerticalPipe(
    color: Color,
) {
    Box(
        modifier = Modifier
            .height(20.dp)
            .width(1.dp)
            .background(color),
    )
}