package com.sliverzfx.gothicquest

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

/** Static fallback for the older app entry point; V2 animates its intro inside HomeScreen. */
@Composable
fun SplashScreen() {
    Box(Modifier.fillMaxSize().background(Color.Black).testTag("splash_screen"),
        contentAlignment = Alignment.Center) {
        Image(painterResource(R.drawable.home_background), contentDescription = null,
            modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
        QuestboundBrand(Modifier.padding(horizontal = 20.dp))
    }
}
