package android.reagent.designsystem

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val ReagentColorScheme = darkColorScheme(
    primary = NeonYellow,
    onPrimary = Nero,
    secondary = NeonYellow,
    background = Nero,
    onBackground = Platinum,
    surface = Nero,
    onSurface = Platinum,
    onSurfaceVariant = Nero,
    surfaceVariant = Platinum,
    outline = Platinum,
    error = Vermilion
)

@Composable
fun ReagentTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ReagentColorScheme,
        typography = Typography,
        content = content
    )
}