package android.reagent.tester.components

import android.reagent.data.R
import android.reagent.designsystem.Nero
import android.reagent.designsystem.NeonYellow
import android.reagent.designsystem.ReagentTheme
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RunRequestButton(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(durationMillis = 120),
        label = "button_scale"
    )

    Button(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(25.dp))
            .height(56.dp)
            .scale(scale)
            .background(NeonYellow),

        enabled = enabled && !loading,

        onClick = onClick,

        shape = RoundedCornerShape(16.dp)
    ) {

        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = Nero,
                strokeWidth = 2.dp
            )
        } else {
            Icon(
                painter = painterResource(android.reagent.tester.R.drawable.ic_play),
                contentDescription = null
            )
        }

        Spacer(
            Modifier.width(8.dp)
        )

        Text(
            text = if (loading) "Running..." else "Run Request"
        )
    }
}

@Preview
@Composable
private fun RunRequestButton_Preview() {
    ReagentTheme {
        Column {
            RunRequestButton(
                loading = false,
                onClick = {}
            )
            Spacer(Modifier.height(8.dp))
            RunRequestButton(
                loading = true,
                onClick = {}
            )
        }
    }
}
