package android.reagent.tester

import android.content.ClipboardManager
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun EndpointTesterRoute(
    viewModel: EndpointTesterViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    EndpointTesterScreen(
        state = state,
        onUrlChange = viewModel::updateUrl,
        onRunRequest = viewModel::runTest,
        onRunRecentRequest = viewModel::runRecentEndpoint,
        onDeleteResult = viewModel::deleteResult,
        onPasteFromClipboard = {
            val clipboardText = readClipboardText(context)

            if (clipboardText != null) {
                viewModel.updateUrl(clipboardText)
            }
        }
    )
}

private fun readClipboardText(
    context: Context,
): String? {
    val clipboardManager = context.getSystemService(
        Context.CLIPBOARD_SERVICE,
    ) as ClipboardManager

    val clip = clipboardManager.primaryClip
        ?: return null

    if (clip.itemCount == 0) {
        return null
    }

    return clip
        .getItemAt(0)
        .coerceToText(context)
        ?.toString()
        ?.trim()
        ?.takeIf(String::isNotEmpty)
}