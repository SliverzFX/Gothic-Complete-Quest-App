package com.sliverzfx.gothicquest

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.testTag

private val Gold = Color(0xFFD2AE68)
private val DarkGold = Color(0xFF6F5227)
private val Ember = Color(0xFF8B2419)

@Composable
fun HomeScreen() {
    Box(
        Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF120A08), Color(0xFF070606), Color.Black)
                )
            )
            .testTag("home_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp, vertical = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "GOTHIC",
                color = Gold,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 5.sp
            )
            Text(
                text = "COMPLETE QUEST GUIDE",
                color = Color(0xFFBCA77D),
                fontSize = 12.sp,
                letterSpacing = 2.sp
            )

            Spacer(Modifier.height(26.dp))

            GameCard(
                title = "Gothic",
                subtitle = "THE COLONY",
                accent = Color(0xFFBFC3C9),
                modifier = Modifier.testTag("game_gothic")
            )
            Spacer(Modifier.height(14.dp))
            GameCard(
                title = "Gothic II",
                subtitle = "GOLD EDITION",
                accent = Color(0xFFE1B34E),
                modifier = Modifier.testTag("game_gothic_2")
            )
            Spacer(Modifier.height(14.dp))
            GameCard(
                title = "Gothic II",
                subtitle = "NEW BALANCE",
                accent = Color(0xFFB7B9BD),
                modifier = Modifier.testTag("game_new_balance")
            )

            Spacer(Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FooterItem("★", "Favorites")
                FooterItem("⚙", "Settings")
                FooterItem("ⓘ", "Info")
            }
        }
    }
}

@Composable
private fun GameCard(
    title: String,
    subtitle: String,
    accent: Color,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(7.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(148.dp)
            .border(1.dp, DarkGold, shape)
            .background(
                Brush.horizontalGradient(
                    listOf(
                        Color(0xFF17110E),
                        Color(0xFF32120E),
                        Color(0xFF17110E)
                    )
                ),
                shape
            )
            .clickable { }
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .border(1.dp, Ember.copy(alpha = 0.55f), shape),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = title,
                    color = accent,
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = subtitle,
                    color = Gold,
                    fontSize = 13.sp,
                    letterSpacing = 3.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun FooterItem(icon: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(icon, color = Gold, fontSize = 21.sp)
        Text(label, color = Color(0xFF9E8D70), fontSize = 10.sp)
    }
}
