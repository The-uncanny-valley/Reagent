package android.reagent.tester.components

import android.reagent.designsystem.NeonGreen
import android.reagent.designsystem.Nero
import android.reagent.designsystem.Vermilion
import android.reagent.tester.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ResultStatusBadge(
    success: Boolean,
) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(
                if (success) {
                    NeonGreen
                } else {
                    Vermilion
                }
            )
    ) {
        val icon = if (success) {
            painterResource(R.drawable.ic_tick)
        } else {
            painterResource(R.drawable.ic_cross)
        }

        Icon(
            modifier = Modifier.align(Alignment.Center),
            painter = icon,
            tint = Nero,
            contentDescription = null
        )
    }
}

@Preview
@Composable
private fun ResultStatusBadgePreview() {
    ResultStatusBadge(success = true)
}