package com.sliverzfx.gothicquest

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView

/** Owned above the route crossfade so navigation never replaces the video surface. */
internal class MenuBackgroundState {
    var playbackRequested by mutableStateOf(false)
    var revealVideo by mutableStateOf(false)
    var frameReady by mutableStateOf(false)
}

internal val AppRoute.usesMenuBackground: Boolean
    get() = this == AppRoute.Home || this == AppRoute.ContinueHistory || this == AppRoute.Settings || this == AppRoute.Donations ||
        this == AppRoute.Support || this is AppRoute.QuestCorrection || this == AppRoute.About || this == AppRoute.Faqs ||
        this == AppRoute.GameTools || this is AppRoute.ToolGames || this is AppRoute.ToolReference

@Composable
internal fun MenuBackground(
    state: MenuBackgroundState,
    animationEnabled: Boolean,
    visible: Boolean = true,
    dimmed: Boolean = false
) {
    val context = LocalContext.current
    val reduceAnimations = LocalReduceAnimations.current
    val playVideo = animationEnabled && !reduceAnimations
    val video = remember(context, playVideo) {
        state.frameReady = false
        if (playVideo) HomeVideoView(context) { state.frameReady = it } else null
    }
    DisposableEffect(video) {
        onDispose { video?.dispose() }
    }
    val videoOpacity by animateFloatAsState(
        if (playVideo && state.revealVideo && state.frameReady) 1f else 0f,
        tween(if (reduceAnimations) 0 else 900), label = "menuVideoCrossfade")
    val dimOpacity by animateFloatAsState(if (dimmed) 0.60f else 0f,
        tween(if (reduceAnimations) 0 else 350), label = "menuBackgroundDimming")

    Box(Modifier.fillMaxSize().alpha(if (visible) 1f else 0f).testTag("shared_menu_background")) {
        Image(painter = painterResource(R.drawable.home_background),
            contentDescription = "Khorinis", contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().testTag("home_background_still"))
        if (video != null) {
            AndroidView(factory = { video },
                update = { it.requestPlayback(state.playbackRequested && visible) },
                modifier = Modifier.fillMaxSize().alpha(videoOpacity).testTag("home_background_video"))
        }
        Box(Modifier.fillMaxSize().background(Color.Black.copy(alpha = dimOpacity))
            .testTag("menu_background_dimmer"))
    }
}
