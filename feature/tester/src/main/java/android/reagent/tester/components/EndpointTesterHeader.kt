package android.reagent.tester.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign

@Composable
fun EndpointTesterHeader() {
    Text(
        text = "Endpoint Tester",
        style = MaterialTheme.typography.titleMedium
    )
}