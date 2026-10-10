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
 * Pixel Tablet / large landscape page layout only.
 * Compact 7-inch tablets and phones intentionally retain their current pages.
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
    Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
        Row(
            Modifier.widthIn(max = 1100.dp).fillMaxSize().padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(22.dp)
        ) {
            Column(
                Modifier.weight(1f).fillMaxHeight().verticalScroll(rememberScrollState())
                    .padding(bottom = 24.dp),
                content = left
            )
            Column(
                Modifier.weight(1f).fillMaxHeight().verticalScroll(rememberScrollState())
                    .padding(bottom = 24.dp),
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
    Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
        Column(
            Modifier.widthIn(max = maxWidthDp.dp).fillMaxSize()
                .verticalScroll(rememberScrollState()).padding(top = 18.dp, bottom = 24.dp),
            content = content
        )
    }
}
