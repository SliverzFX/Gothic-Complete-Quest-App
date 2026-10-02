package com.sliverzfx.gothicquest

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val LibraryGold = Color(0xFFC79A55)
private val LibraryGoldDark = Color(0xFF765225)

data class GameLibraryEntry(
    val id: String,
    val title: String,
    val panelRes: Int,
    val logoRes: Int?,
    val testTag: String,
    val onClick: () -> Unit
)

@Composable
fun GameLibraryScreen(
    title: String,
    entries: List<GameLibraryEntry>,
    onBack: () -> Unit,
    topRightActionLabel: String? = null,
    onTopRightAction: (() -> Unit)? = null
) {
    BackHandler(onBack = onBack)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .testTag("game_library_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
                .padding(top = 56.dp, bottom = 92.dp)
        ) {
            if (entries.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title,
                        color = Color(0xFFB6935B),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            } else {
                entries.forEachIndexed { index, entry ->
                    GameLibraryPanel(entry)
                    if (index != entries.lastIndex) GoldDivider()
                }
            }
        }

        Image(
            painter = painterResource(R.drawable.menu_smoke),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .align(Alignment.TopCenter)
                .rotate(180f),
            contentScale = ContentScale.FillBounds
        )

        Image(
            painter = painterResource(R.drawable.menu_smoke),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .align(Alignment.BottomCenter),
            contentScale = ContentScale.FillBounds
        )

        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "‹  BACK",
                color = Color(0xFFD3B071),
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clickable(onClick = onBack)
                    .padding(8.dp)
                    .testTag("library_back")
            )
            if (topRightActionLabel != null && onTopRightAction != null) {
                Text(
                    text = topRightActionLabel,
                    color = Color(0xFFD3B071),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .clickable(onClick = onTopRightAction)
                        .padding(8.dp)
                        .testTag("library_top_right_action")
                )
            }
        }
    }
}

@Composable
private fun GameLibraryPanel(entry: GameLibraryEntry) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clickable(onClick = entry.onClick)
            .testTag(entry.testTag),
        contentAlignment = Alignment.BottomEnd
    ) {
        Image(
            painter = painterResource(entry.panelRes),
            contentDescription = entry.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0x18000000), Color.Transparent, Color(0x59000000))
                    )
                )
        )
        entry.logoRes?.let { logo ->
            Image(
                painter = painterResource(logo),
                contentDescription = null,
                modifier = Modifier
                    .width(190.dp)
                    .padding(end = 14.dp, bottom = 12.dp)
                    .offset(x = 5.dp, y = 6.dp)
                    .blur(6.dp)
                    .alpha(0.68f),
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(Color.Black)
            )
            Image(
                painter = painterResource(logo),
                contentDescription = null,
                modifier = Modifier
                    .width(190.dp)
                    .padding(end = 14.dp, bottom = 12.dp)
                    .offset(x = 2.dp, y = 3.dp)
                    .blur(2.5.dp)
                    .alpha(0.52f),
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(Color.Black)
            )
            Image(
                painter = painterResource(logo),
                contentDescription = null,
                modifier = Modifier
                    .width(190.dp)
                    .padding(end = 14.dp, bottom = 12.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
private fun GoldDivider() {
    Box(
        Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(
                Brush.horizontalGradient(
                    listOf(
                        Color.Transparent,
                        LibraryGoldDark,
                        LibraryGold,
                        LibraryGoldDark,
                        Color.Transparent
                    )
                )
            )
    )
}
