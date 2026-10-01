package com.sliverzfx.gothicquest

import android.net.Uri
import android.view.View
import android.widget.VideoView
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

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
    onContinue: () -> Unit = {},
    onDestinationSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val backgroundVideo = remember(context) {
        VideoView(context).apply {
            setVideoURI(
                Uri.parse("android.resource://${context.packageName}/${R.raw.home_menu_loop}")
            )
            setOnPreparedListener { mediaPlayer ->
                mediaPlayer.isLooping = true
                mediaPlayer.setVolume(0f, 0f)
                start()
            }
            setOnErrorListener { _, _, _ ->
                visibility = View.GONE
                true
            }
            start()
        }
    }

    DisposableEffect(backgroundVideo) {
        onDispose {
            backgroundVideo.stopPlayback()
        }
    }

    val entries = buildList {
        if (hasContinue) add(HomeMenuEntry("CONTINUE", "home_continue", onContinue))
        add(HomeMenuEntry("QUEST GUIDES", "home_quest_guides") { onDestinationSelected("Quest Guides") })
        add(HomeMenuEntry("MARVIN CODES / CHEATS", "home_cheats") { onDestinationSelected("Marvin Codes / Cheats") })
        add(HomeMenuEntry("FAQs", "home_faqs") { onDestinationSelected("FAQs") })
        add(HomeMenuEntry("INFO / ABOUT", "home_about") { onDestinationSelected("Info / About") })
        add(HomeMenuEntry("SUPPORT / BUGS", "home_support") { onDestinationSelected("Support / Bugs") })
        add(HomeMenuEntry("DONATIONS", "home_donations") { onDestinationSelected("Donations") })
        add(HomeMenuEntry("SETTINGS", "home_settings") { onDestinationSelected("Settings") })
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .testTag("home_screen")
    ) {
        // Static fallback stays underneath the video in case playback is unavailable on a device.
        Image(
            painter = painterResource(R.drawable.home_background),
            contentDescription = "Khorinis",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        AndroidView(
            factory = { backgroundVideo },
            update = { videoView ->
                if (videoView.visibility == View.VISIBLE && !videoView.isPlaying) {
                    videoView.start()
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .testTag("home_background_video")
        )

        // Only darken the right side enough to keep the title-menu readable.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x12000000))
        )

        Column(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(end = 22.dp)
                .widthIn(min = 205.dp, max = 260.dp),
            horizontalAlignment = Alignment.End
        ) {
            entries.forEachIndexed { index, entry ->
                GothicMenuItem(entry)
                if (index != entries.lastIndex) Spacer(Modifier.height(13.dp))
            }
        }
    }
}

@Composable
private fun GothicMenuItem(entry: HomeMenuEntry) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val color by animateColorAsState(
        targetValue = if (pressed) HomeMenuGoldPressed else HomeMenuGold,
        animationSpec = tween(110),
        label = "menuTextColor"
    )
    val scale by animateFloatAsState(
        targetValue = if (pressed) 1.035f else 1f,
        animationSpec = tween(110),
        label = "menuTextScale"
    )

    Row(
        modifier = Modifier
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = entry.action
            )
            .padding(horizontal = 4.dp, vertical = 3.dp)
            .testTag(entry.testTag),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GothicSelectionRune(visible = pressed)
        Text(
            text = entry.label,
            color = color,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.7.sp,
            style = TextStyle(
                shadow = Shadow(
                    color = if (pressed) Color(0xD9D09A44) else Color(0x88000000),
                    blurRadius = if (pressed) 13f else 3f
                )
            )
        )
    }
}

@Composable
private fun GothicSelectionRune(visible: Boolean) {
    Box(
        modifier = Modifier.widthIn(min = 23.dp),
        contentAlignment = Alignment.Center
    ) {
        if (visible) {
            Text(
                text = "✦",
                color = HomeMenuGoldPressed,
                fontSize = 15.sp,
                style = TextStyle(
                    shadow = Shadow(color = Color(0xD9D09A44), blurRadius = 10f)
                )
            )
        }
    }
}
