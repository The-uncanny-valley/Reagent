package android.reagent.tester.components

import android.reagent.designsystem.Nero
import android.reagent.designsystem.Platinum
import android.reagent.designsystem.ReagentTheme
import android.reagent.tester.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun EndpointInputField(
    value: String,
    onValueChange: (String) -> Unit,
    onPasteFromClipboard: () -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp)),

        singleLine = true,

        colors = TextFieldDefaults.colors(
            focusedContainerColor = Platinum,
            unfocusedContainerColor = Platinum,

            focusedTextColor = Nero,
            unfocusedTextColor = Nero,

            cursorColor = Nero,

            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,

            disabledIndicatorColor = Color.Transparent,
        ),

        leadingIcon = {
            IconButton(
                onClick = onPasteFromClipboard
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_clipboard),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        },

        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(
                    onClick = { onValueChange("") }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_cross),
                        contentDescription = "Clear",
                        tint = Nero
                    )
                }
            }
        },
    )
}

@Preview
@Composable
private fun EndpointInputFieldPreview() {
    ReagentTheme {
        EndpointInputField(
            value = "example.com",
            onValueChange = {},
            onPasteFromClipboard = {}
        )
    }
}