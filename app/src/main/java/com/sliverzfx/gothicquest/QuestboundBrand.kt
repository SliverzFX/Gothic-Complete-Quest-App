package com.sliverzfx.gothicquest

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
internal fun QuestboundBrand(modifier: Modifier = Modifier, logoFraction: Float = 1f) {
    val logo = painterResource(R.drawable.questbound_logo)
    Box(modifier.testTag("questbound_brand"), contentAlignment = Alignment.Center) {
        Image(logo, contentDescription = "Questbound — RPG Quest Guide",
            modifier = Modifier.widthIn(max = 420.dp).fillMaxWidth(logoFraction)
                .aspectRatio(logo.intrinsicSize.width / logo.intrinsicSize.height),
            contentScale = ContentScale.Fit)
    }
}

@Composable
internal fun QuestboundCredit(modifier: Modifier = Modifier) {
    val credit = painterResource(R.drawable.sliverzfx_credit)
    Image(credit, contentDescription = "by SliverzFx",
        modifier = modifier.aspectRatio(credit.intrinsicSize.width / credit.intrinsicSize.height)
            .testTag("questbound_credit"), contentScale = ContentScale.Fit)
}
