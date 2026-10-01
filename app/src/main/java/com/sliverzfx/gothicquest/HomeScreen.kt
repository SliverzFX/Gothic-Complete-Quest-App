package com.sliverzfx.gothicquest

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val MenuGold = Color(0xFFC79A55)
private val MenuGoldDark = Color(0xFF765225)

@Composable
fun HomeScreen(
    hasContinue: Boolean = false,
    onContinue: () -> Unit = {},
    onDestinationSelected: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .testTag("home_screen")
    ) {
        Image(
            painter = painterResource(R.drawable.home_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0x66000000),
                            Color(0x22000000),
                            Color(0x55000000)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            TopControls(
                hasContinue = hasContinue,
                onContinue = onContinue,
                onFavorites = { onDestinationSelected("Favorites") },
                onSettings = { onDestinationSelected("Settings") },
                onAbout = { onDestinationSelected("About") }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                GamePanel(
                    imageRes = R.drawable.gothic_button_1,
                    logoRes = R.drawable.gothic_classic_logo,
                    contentDescription = "Gothic",
                    testTag = "game_gothic",
                    onClick = { onDestinationSelected("Gothic") }
                )
                GoldDivider()
                GamePanel(
                    imageRes = R.drawable.gothic_button_2,
                    logoRes = R.drawable.gothic_2_gold_logo,
                    contentDescription = "Gothic II Gold Edition",
                    testTag = "game_gothic_2",
                    onClick = { onDestinationSelected("Gothic II Gold Edition") }
                )
                GoldDivider()
                GamePanel(
                    imageRes = R.drawable.gothic_button_nb,
                    logoRes = R.drawable.gothic_2_new_balance_logo,
                    contentDescription = "Gothic II New Balance",
                    testTag = "game_new_balance",
                    onClick = { onDestinationSelected("Gothic II New Balance") }
                )
            }
        }
    }
}

@Composable
private fun TopControls(
    hasContinue: Boolean,
    onContinue: () -> Unit,
    onFavorites: () -> Unit,
    onSettings: () -> Unit,
    onAbout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xB80A0806))
            .border(width = 1.dp, color = MenuGoldDark)
            .padding(horizontal = 12.dp, vertical = 9.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (hasContinue) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .background(Color(0xDD17110C), RoundedCornerShape(5.dp))
                    .border(1.dp, MenuGold, RoundedCornerShape(5.dp))
                    .clickable(onClick = onContinue)
                    .testTag("continue"),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "CONTINUE",
                    color = Color(0xFFE2C184),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
            Spacer(Modifier.height(8.dp))
        }

        androidx.compose.foundation.layout.Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ControlButton("★  FAVORITES", "favorites", onFavorites)
            ControlButton("⚙  SETTINGS", "settings", onSettings)
            ControlButton("ⓘ  ABOUT", "about", onAbout)
        }
    }
}

@Composable
private fun ControlButton(label: String, tag: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .height(36.dp)
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp)
            .testTag(tag),
        contentAlignment = Alignment.Center
    ) {
        Text(
            label,
            color = Color(0xFFD5B273),
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun GamePanel(
    imageRes: Int,
    logoRes: Int,
    contentDescription: String,
    testTag: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clickable(onClick = onClick)
            .testTag(testTag),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0x22000000), Color.Transparent, Color(0x66000000))
                    )
                )
        )
        Image(
            painter = painterResource(logoRes),
            contentDescription = null,
            modifier = Modifier
                .width(245.dp)
                .shadow(8.dp),
            contentScale = ContentScale.Fit
        )
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
                        MenuGoldDark,
                        MenuGold,
                        MenuGoldDark,
                        Color.Transparent
                    )
                )
            )
    )
}
