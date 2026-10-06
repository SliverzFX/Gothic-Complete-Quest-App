package com.sliverzfx.gothicquest

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withTimeoutOrNull
import androidx.compose.runtime.snapshotFlow

private val HomeMenuGold = Color(0xFFC7A469)
private val HomeMenuGoldPressed = Color(0xFFFFD98A)

private data class HomeMenuEntry(
    val label: String,
    val testTag: String,
    val action: () -> Unit
)

@Composable
fun HomeScreen(
    hasContinue: Boolean = false,
    playIntro: Boolean = false,
    onIntroFinished: () -> Unit = {},
    onExit: () -> Unit = {},
    onContinue: () -> Unit = {},
    onDestinationSelected: (String) -> Unit
) {
    val reduceAnimations = LocalReduceAnimations.current
    val context = LocalContext.current
    // Capture once: finishing the intro does not restart the sequence on recomposition.
    val introRequested = remember { playIntro && !reduceAnimations }
    var titleVisible by remember { mutableStateOf(!introRequested) }
    var titleAtTop by remember { mutableStateOf(!introRequested) }
    var menuVisible by remember { mutableStateOf(!introRequested) }
    var playbackRequested by remember { mutableStateOf(!introRequested) }
    var videoReady by remember { mutableStateOf(false) }
    val backgroundVideo = remember(context, reduceAnimations) {
        if (reduceAnimations) null else HomeVideoView(context) { videoReady = it }
    }
    DisposableEffect(backgroundVideo) {
        onDispose { backgroundVideo?.dispose() }
    }
    LaunchedEffect(reduceAnimations) {
        if (introRequested && !reduceAnimations && !menuVisible) {
            delay(1000)
            titleVisible = true
            delay(2200)
            playbackRequested = true
            // Begin the handoff after a decoded frame; unsupported video must not block startup.
            withTimeoutOrNull(2000) { snapshotFlow { videoReady }.first { it } }
            titleAtTop = true
            delay(900) // Crossfade and title movement finish together.
            delay(1000)
        }
        titleVisible = true
        playbackRequested = true
        titleAtTop = true
        menuVisible = true
        onIntroFinished()
    }
    val titleOpacity by animateFloatAsState(if (titleVisible) 1f else 0f,
        tween(if (reduceAnimations) 0 else 650), label = "introTitleOpacity")
    val titlePosition by animateFloatAsState(if (titleAtTop) 1f else 0f,
        tween(if (reduceAnimations) 0 else 900, easing = FastOutSlowInEasing), label = "introTitlePosition")
    val videoOpacity by animateFloatAsState(if (titleAtTop && videoReady) 1f else 0f,
        tween(if (reduceAnimations) 0 else 900), label = "introVideoCrossfade")
    val menuBackdropOpacity by animateFloatAsState(if (titleAtTop) 1f else 0f,
        tween(if (reduceAnimations) 0 else 900), label = "introMenuBackdrop")

    val entries = buildList {
        if (hasContinue) add(HomeMenuEntry("CONTINUE", "home_continue", onContinue))
        add(HomeMenuEntry("QUEST GUIDES", "home_quest_guides") { onDestinationSelected("Quest Guides") })
        add(HomeMenuEntry("MARVIN", "home_cheats") { onDestinationSelected("Marvin Codes / Cheats") })
        add(HomeMenuEntry("FAQs", "home_faqs") { onDestinationSelected("FAQs") })
        add(HomeMenuEntry("ABOUT", "home_about") { onDestinationSelected("Info / About") })
        add(HomeMenuEntry("SUPPORT", "home_support") { onDestinationSelected("Support / Bugs") })
        add(HomeMenuEntry("DONATIONS", "home_donations") { onDestinationSelected("Donations") })
        add(HomeMenuEntry("SETTINGS", "home_settings") { onDestinationSelected("Settings") })
        add(HomeMenuEntry("EXIT", "home_exit", onExit))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .testTag("home_screen")
    ) {
        Image(
            painter = painterResource(R.drawable.home_background),
            contentDescription = "Khorinis",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        if (backgroundVideo != null) {
            AndroidView(
                factory = { backgroundVideo },
                update = { videoView -> videoView.requestPlayback(playbackRequested) },
                modifier = Modifier.fillMaxSize().alpha(videoOpacity)
                    .testTag("home_background_video")
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x10000000)).alpha(menuBackdropOpacity)
        )

        Image(
            painter = painterResource(R.drawable.main_menu_smoke),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.88f * menuBackdropOpacity)
                .testTag("home_menu_smoke"),
            contentScale = ContentScale.FillBounds
        )

        BoxWithConstraints(Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding()) {
            val density = LocalDensity.current
            var titleHeight by remember { mutableStateOf(120.dp) }
            var creditHeight by remember { mutableStateOf(60.dp) }
            val centeredY = ((maxHeight - titleHeight - creditHeight - 12.dp) / 2).coerceAtLeast(16.dp)
            val titleY = centeredY + (16.dp - centeredY) * titlePosition
            QuestboundBrand(
                modifier = Modifier.align(Alignment.TopCenter).offset(y = titleY)
                    .fillMaxWidth().padding(horizontal = 20.dp).alpha(titleOpacity)
                    .onSizeChanged { titleHeight = with(density) { it.height.toDp() } },
                logoFraction = 0.96f - 0.08f * titlePosition
            )
            val creditStartY = centeredY + titleHeight + 12.dp
            val creditEndY = (maxHeight - creditHeight - 12.dp).coerceAtLeast(16.dp)
            val creditY = creditStartY + (creditEndY - creditStartY) * titlePosition
            QuestboundCredit(Modifier.align(Alignment.TopCenter).offset(y = creditY)
                .width(180.dp).alpha(titleOpacity)
                .onSizeChanged { creditHeight = with(density) { it.height.toDp() } })
            if (!menuVisible) {
                Box(Modifier.fillMaxSize().testTag("splash_screen"))
            }
            Box(Modifier.fillMaxSize().padding(top = titleHeight + 40.dp, bottom = creditHeight + 32.dp)) {
                Column(
                    modifier = Modifier.align(Alignment.CenterEnd)
                        .padding(end = 24.dp).widthIn(min = 235.dp, max = 310.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.End
                ) {
                    entries.forEachIndexed { index, entry ->
                        AnimatedVisibility(
                            visible = menuVisible,
                            enter = fadeIn(tween(if (reduceAnimations) 0 else 400,
                                delayMillis = if (reduceAnimations) 0 else index * 65)) +
                                slideInHorizontally(tween(if (reduceAnimations) 0 else 450,
                                    delayMillis = if (reduceAnimations) 0 else index * 65)) { it / 5 }
                        ) {
                            Column(horizontalAlignment = Alignment.End) {
                                GothicMenuItem(entry)
                                if (index != entries.lastIndex) {
                                    Spacer(Modifier.height(8.dp))
                                    Box(Modifier.width(210.dp).height(1.dp).background(
                                        Brush.horizontalGradient(listOf(Color.Transparent,
                                            Color(0x66A67C32), Color(0xB8E0BD69),
                                            Color(0x66A67C32), Color.Transparent))))
                                    Spacer(Modifier.height(5.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GothicMenuItem(entry: HomeMenuEntry) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val reduceAnimations = LocalReduceAnimations.current
    val scale by animateFloatAsState(
        targetValue = if (pressed && !reduceAnimations) 1.16f else 1f,
        animationSpec = tween(if (reduceAnimations) 0 else 170),
        label = "menuTextScale"
    )
    val verticalPadding by animateDpAsState(
        targetValue = if (pressed && !reduceAnimations) 14.dp else 5.dp,
        animationSpec = tween(if (reduceAnimations) 0 else 170),
        label = "menuItemSpacing"
    )
    val goldBrush = if (pressed) {
        Brush.verticalGradient(
            listOf(
                Color(0xFFFFF0B0),
                Color(0xFFFFD66F),
                Color(0xFFD89B2B),
                Color(0xFFFFDF82)
            )
        )
    } else {
        Brush.verticalGradient(
            listOf(
                Color(0xFFFFE6A6),
                Color(0xFFE6C57B),
                Color(0xFFB58B43),
                Color(0xFFF2D591)
            )
        )
    }

    Row(
        modifier = Modifier
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = entry.action
            )
            .padding(horizontal = 4.dp, vertical = verticalPadding)
            .testTag(entry.testTag),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GothicSelectionRune(visible = pressed)
        Text(
            text = entry.label,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.0.sp,
            style = TextStyle(
                brush = goldBrush,
                shadow = Shadow(
                    color = if (pressed) Color(0xE0D09A44) else Color(0xB0000000),
                    blurRadius = if (pressed) 15f else 4f
                )
            )
        )
    }
}

@Composable
private fun GothicSelectionRune(visible: Boolean) {
    Box(
        modifier = Modifier.widthIn(min = 25.dp),
        contentAlignment = Alignment.Center
    ) {
        if (visible) {
            Text(
                text = "✦",
                color = HomeMenuGoldPressed,
                fontSize = 16.sp,
                style = TextStyle(
                    shadow = Shadow(color = Color(0xD9D09A44), blurRadius = 10f)
                )
            )
        }
    }
}
