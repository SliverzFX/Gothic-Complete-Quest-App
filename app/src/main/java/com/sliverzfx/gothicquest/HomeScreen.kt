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
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
internal fun HomeScreen(
    hasContinue: Boolean = false,
    playIntro: Boolean = false,
    backgroundAnimationEnabled: Boolean = true,
    backgroundState: MenuBackgroundState? = null,
    onIntroFinished: () -> Unit = {},
    onExit: () -> Unit = {},
    onContinue: () -> Unit = {},
    onDestinationSelected: (String) -> Unit
) {
    val reduceAnimations = LocalReduceAnimations.current
    val tabletLandscape = isLandscapeTablet()
    val tabletScale = tabletLayoutScale()
    val compactTablet = isCompactLandscapeTablet()
    val backdrop = backgroundState ?: remember { MenuBackgroundState() }
    val animateBackground = backgroundAnimationEnabled && !reduceAnimations && !tabletLandscape
    // Capture once: finishing the intro does not restart the sequence on recomposition.
    val introRequested = remember { playIntro && !reduceAnimations }
    var titleVisible by remember { mutableStateOf(!introRequested) }
    var titleAtTop by remember { mutableStateOf(!introRequested) }
    var menuVisible by remember { mutableStateOf(!introRequested) }
    LaunchedEffect(reduceAnimations, animateBackground) {
        if (introRequested && !reduceAnimations && !menuVisible) {
            delay(1000)
            titleVisible = true
            delay(2200)
            backdrop.playbackRequested = true
            // A still background needs no video handoff or frame-readiness delay.
            if (animateBackground) {
                withTimeoutOrNull(2000) { snapshotFlow { backdrop.frameReady }.first { it } }
            }
            titleAtTop = true
            backdrop.revealVideo = true
            delay(900) // Crossfade and title movement finish together.
            delay(1000)
        }
        titleVisible = true
        backdrop.playbackRequested = true
        titleAtTop = true
        backdrop.revealVideo = true
        menuVisible = true
        onIntroFinished()
    }
    val titleOpacity by animateFloatAsState(if (titleVisible) 1f else 0f,
        tween(if (reduceAnimations) 0 else 650), label = "introTitleOpacity")
    val titlePosition by animateFloatAsState(if (titleAtTop) 1f else 0f,
        tween(if (reduceAnimations) 0 else 900, easing = FastOutSlowInEasing), label = "introTitlePosition")
    val menuBackdropOpacity by animateFloatAsState(if (titleAtTop) 1f else 0f,
        tween(if (reduceAnimations) 0 else 900), label = "introMenuBackdrop")

    val entries = buildList {
        if (hasContinue) add(HomeMenuEntry("CONTINUE", "home_continue", onContinue))
        add(HomeMenuEntry("PICK A GAME", "home_quest_guides") { onDestinationSelected("Quest Guides") })
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
            .testTag("home_screen")
    ) {
        if (backgroundState == null) {
            MenuBackground(backdrop, backgroundAnimationEnabled)
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
            // Phone dimensions remain unchanged. Tablet-landscape layouts get larger controls.
            var titleHeight by remember { mutableStateOf(120.dp) }
            var creditHeight by remember { mutableStateOf(60.dp) }
            val centeredY = ((maxHeight - titleHeight - creditHeight - 12.dp) / 2).coerceAtLeast(16.dp)
            val titleY = centeredY + (16.dp - centeredY) * titlePosition
            QuestboundBrand(
                modifier = Modifier.align(Alignment.TopCenter).offset(y = titleY)
                    .fillMaxWidth().padding(horizontal = 20.dp).alpha(titleOpacity)
                    .onSizeChanged { titleHeight = with(density) { it.height.toDp() } },
                logoFraction = 0.96f - 0.08f * titlePosition,
                maxLogoWidth = if (tabletLandscape) 560.dp * tabletScale else 420.dp
            )
            val creditStartY = centeredY + titleHeight + 12.dp
            val creditEndY = (maxHeight - creditHeight - 12.dp).coerceAtLeast(16.dp)
            val creditY = creditStartY + (creditEndY - creditStartY) * titlePosition
            QuestboundCredit(Modifier.align(Alignment.TopCenter).offset(y = creditY)
                .width(180.dp * tabletScale).alpha(titleOpacity)
                .onSizeChanged { creditHeight = with(density) { it.height.toDp() } })
            if (!menuVisible) {
                Box(Modifier.fillMaxSize().testTag("splash_screen"))
            }
            // The compact tablet has a shorter display: avoid reserving an entire
            // second logo/credit-sized band around the vertical menu.
            // Pixel Tablet: reclaim only decorative whitespace, not text or button size.
            // This keeps EXIT reachable without having to scroll the main menu.
            val largeTablet = tabletLandscape && !compactTablet
            val menuTop = when {
                compactTablet -> titleHeight + 4.dp
                largeTablet -> titleHeight + 26.dp
                else -> titleHeight + 40.dp * tabletScale
            }
            val menuBottom = when {
                compactTablet -> 8.dp
                largeTablet -> creditHeight + 8.dp
                else -> creditHeight + 32.dp * tabletScale
            }
            Box(Modifier.fillMaxSize().padding(top = menuTop, bottom = menuBottom)) {
                Column(
                    modifier = Modifier.align(Alignment.CenterEnd)
                        .padding(end = if (tabletLandscape) 44.dp * tabletScale else 24.dp)
                        .widthIn(
                            min = if (tabletLandscape) 330.dp * tabletScale else 235.dp,
                            max = if (tabletLandscape) 380.dp * tabletScale else 310.dp
                        )
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
                                GothicMenuItem(entry, tabletLandscape, tabletScale, compactTablet)
                                if (index != entries.lastIndex) {
                                    Spacer(Modifier.height(if (compactTablet) 1.dp else if (largeTablet) 4.dp else if (tabletLandscape) 6.dp * tabletScale else 8.dp))
                                    Box(Modifier.width(if (tabletLandscape) 290.dp * tabletScale else 210.dp).height(1.dp).background(
                                        Brush.horizontalGradient(listOf(Color.Transparent,
                                            Color(0x66A67C32), Color(0xB8E0BD69),
                                            Color(0x66A67C32), Color.Transparent))))
                                    Spacer(Modifier.height(if (compactTablet) 1.dp else if (largeTablet) 2.dp else if (tabletLandscape) 4.dp * tabletScale else 5.dp))
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
private fun GothicMenuItem(entry: HomeMenuEntry, tabletLandscape: Boolean = false,
    tabletScale: Float = 1f, compactTablet: Boolean = false) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val reduceAnimations = LocalReduceAnimations.current
    val scale by animateFloatAsState(
        targetValue = if (pressed && !reduceAnimations) 1.16f else 1f,
        animationSpec = tween(if (reduceAnimations) 0 else 170),
        label = "menuTextScale"
    )
    val verticalPadding by animateDpAsState(
        targetValue = if (pressed && !reduceAnimations) 14.dp else if (compactTablet) 1.dp else if (tabletLandscape) 6.dp * tabletScale else 5.dp,
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

    val shadowBlur = with(LocalDensity.current) { 3.dp.toPx() }

    Row(
        modifier = Modifier
            .then(if (tabletLandscape) Modifier.fillMaxWidth().heightIn(min = if (compactTablet) 48.dp else maxOf(48.dp, 52.dp * tabletScale)) else Modifier)
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = entry.action
            )
            .padding(horizontal = if (tabletLandscape) 8.dp * tabletScale else 4.dp, vertical = verticalPadding)
            .testTag(entry.testTag),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (tabletLandscape) androidx.compose.foundation.layout.Arrangement.End
            else androidx.compose.foundation.layout.Arrangement.Start
    ) {
        GothicSelectionRune(visible = pressed, tabletLandscape = tabletLandscape, tabletScale = tabletScale)
        Box {
            // Keep the shadow separate from the gradient so it is always black.
            Text(
                text = entry.label,
                color = Color.Black,
                fontSize = if (tabletLandscape) (32f * tabletScale).sp else 25.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.0.sp,
                style = TextStyle(shadow = Shadow(color = Color.Black, blurRadius = shadowBlur)),
                modifier = Modifier.offset(x = 2.dp, y = 3.dp).clearAndSetSemantics { }
            )
            Text(
                text = entry.label,
                fontSize = if (tabletLandscape) (32f * tabletScale).sp else 25.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.0.sp,
                style = TextStyle(brush = goldBrush)
            )
        }
    }
}

@Composable
private fun GothicSelectionRune(visible: Boolean, tabletLandscape: Boolean = false, tabletScale: Float = 1f) {
    Box(
        modifier = Modifier.widthIn(min = if (tabletLandscape) 30.dp * tabletScale else 25.dp),
        contentAlignment = Alignment.Center
    ) {
        if (visible) {
            Text(
                text = "✦",
                color = HomeMenuGoldPressed,
                fontSize = if (tabletLandscape) (20f * tabletScale).sp else 16.sp,
                style = TextStyle(
                    shadow = Shadow(color = Color.Black, blurRadius = 10f)
                )
            )
        }
    }
}
