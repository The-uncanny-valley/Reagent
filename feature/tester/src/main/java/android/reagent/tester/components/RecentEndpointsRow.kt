package android.reagent.tester.components

import android.reagent.designsystem.Nero
import android.reagent.designsystem.Platinum
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.reagent.designsystem.ReagentTheme
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ChipColors

@Composable
fun RecentEndpointsRow(
    endpoints: List<String>,
    onRunRecentRequest: (String) -> Unit
) {
    Column {

        Text(
            text = "Recent",
            style = MaterialTheme.typography.labelMedium,
            color = Platinum
        )

        Spacer(
            Modifier.height(8.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(
                items = endpoints,
                key = { endpoint -> endpoint }
            ) { endpoint ->

                AssistChip(
                    onClick = {
                        onRunRecentRequest(endpoint)
                    },
                    modifier = Modifier.height(40.dp),
                    label = {
                        Text(endpoint)
                    },
                    shape = RoundedCornerShape(25.dp),
                    colors = ChipColors(
                        containerColor = Nero,
                        labelColor = Platinum,
                        leadingIconContentColor = Platinum,
                        trailingIconContentColor = Platinum,
                        disabledContainerColor = Nero,
                        disabledLabelColor = Platinum,
                        disabledLeadingIconContentColor = Platinum,
                        disabledTrailingIconContentColor = Platinum,
                    ),
                    border = BorderStroke(color = Platinum, width = 1.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecentEndpointsRowPreview() {
    ReagentTheme {
        RecentEndpointsRow(
            endpoints = listOf(
                "https://api.example.com/v1",
                "https://api.test.org/auth",
                "localhost:8080/debug"
            ),
            onRunRecentRequest = {}
        )
    }
}
