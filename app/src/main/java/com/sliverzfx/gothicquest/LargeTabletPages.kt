package com.sliverzfx.gothicquest

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Full-size Pixel Tablet only. Compact layouts use isCompactLandscapeTablet().
 * Secondary pages opt into these layouts for both tablet sizes separately.
 */
@Composable
internal fun isLargeLandscapeTablet(): Boolean =
    isLandscapeTablet() && !isCompactLandscapeTablet()

/**
 * Two independently scrollable, readable columns under a fixed page header.
 * The width cap keeps content from stretching across very wide displays.
 */
@Composable
internal fun ColumnScope.LargeTabletPageColumns(
    left: @Composable ColumnScope.() -> Unit,
    right: @Composable ColumnScope.() -> Unit
) {
    val compact = isCompactLandscapeTablet()
    Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
        Row(
            Modifier.widthIn(max = if (compact) 980.dp else 1100.dp).fillMaxSize()
                .padding(top = if (compact) 6.dp else 16.dp),
            horizontalArrangement = Arrangement.spacedBy(if (compact) 12.dp else 22.dp)
        ) {
            Column(
                Modifier.weight(1f).fillMaxHeight().verticalScroll(rememberScrollState())
                    .padding(bottom = if (compact) 12.dp else 24.dp),
                content = left
            )
            Column(
                Modifier.weight(1f).fillMaxHeight().verticalScroll(rememberScrollState())
                    .padding(bottom = if (compact) 12.dp else 24.dp),
                content = right
            )
        }
    }
}

/** Centered and scrollable content for pages that do not benefit from a split. */
@Composable
internal fun ColumnScope.LargeTabletPageSingleColumn(
    maxWidthDp: Int = 840,
    content: @Composable ColumnScope.() -> Unit
) {
    val compact = isCompactLandscapeTablet()
    Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
        Column(
            Modifier.widthIn(max = (if (compact) minOf(maxWidthDp, 780) else maxWidthDp).dp)
                .fillMaxSize().verticalScroll(rememberScrollState())
                .padding(top = if (compact) 8.dp else 18.dp,
                    bottom = if (compact) 12.dp else 24.dp),
            content = content
        )
    }
}
