package com.sliverzfx.gothicquest

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density

internal enum class AppTextSize(val label: String, val scale: Float) {
    SMALL("Small", 0.90f),
    NORMAL("Normal", 1.00f),
    LARGE("Large", 1.10f);

    companion object {
        fun fromStoredValue(value: String?): AppTextSize =
            entries.firstOrNull { it.name == value } ?: NORMAL
    }
}

internal fun loadAppTextSize(context: Context): AppTextSize =
    AppTextSize.fromStoredValue(
        context.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE)
            .getString("text_size", null)
    )

internal fun saveAppTextSize(context: Context, size: AppTextSize) {
    context.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE)
        .edit().putString("text_size", size.name).apply()
}

@Composable
internal fun ProvideAppTextSize(size: AppTextSize, content: @Composable () -> Unit) {
    val deviceDensity = LocalDensity.current
    val textDensity = remember(deviceDensity, size) {
        if (size == AppTextSize.NORMAL) deviceDensity
        else Density(
            density = deviceDensity.density,
            fontScale = deviceDensity.fontScale * size.scale
        )
    }
    // Only sp values change; images, spacing, and dp touch targets keep their size.
    CompositionLocalProvider(LocalDensity provides textDensity, content = content)
}
