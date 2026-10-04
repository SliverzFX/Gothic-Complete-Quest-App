package com.sliverzfx.gothicquest

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.delay

@Composable
fun SplashScreen() {
    val reduceAnimations = LocalReduceAnimations.current
    val opacity = remember { Animatable(if (reduceAnimations) 1f else 0f) }
    var fadingOut by remember { mutableStateOf(false) }

    LaunchedEffect(reduceAnimations) {
        if (reduceAnimations) {
            opacity.snapTo(1f)
            return@LaunchedEffect
        }
        opacity.animateTo(1f, tween(550))
        delay(900)
        fadingOut = true
        opacity.animateTo(0f, tween(550))
    }

    Box(
        Modifier.fillMaxSize().background(Color.Black).testTag("splash_screen"),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.splash_art),
            contentDescription = "Gothic Complete Quest Guide",
            modifier = Modifier.fillMaxSize().alpha(opacity.value),
            contentScale = ContentScale.Crop
        )
    }
}
