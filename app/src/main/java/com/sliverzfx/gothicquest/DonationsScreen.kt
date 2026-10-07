package com.sliverzfx.gothicquest

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DonationGold = Color(0xFFD7B06A)
private val DonationBody = Color(0xFFC7B89B)
private const val WiseSupportUrl = "https://wise.com/pay/me/mihag25"

@Composable
internal fun DonationsScreen(onBack: () -> Unit) {
    BackHandler(onBack = onBack)
    val uriHandler = LocalUriHandler.current
    var linkError by remember { mutableStateOf<String?>(null) }
    val shape = RoundedCornerShape(7.dp)

    // The shared menu background owns the video, PNG fallback and page dimming.
    Column(Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding()
        .padding(horizontal = 20.dp).testTag("donations_screen")) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            TextButton(onClick = onBack, modifier = Modifier.heightIn(min = 56.dp).testTag("donations_back")) {
                Text("‹  BACK", color = DonationGold, fontSize = 16.sp)
            }
            TextButton(onClick = onBack, modifier = Modifier.heightIn(min = 56.dp).testTag("donations_home")) {
                Text("HOME", color = DonationGold, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
        Column(Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(bottom = 24.dp)) {
            Text("DONATIONS", color = DonationGold, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(20.dp))
            Column(Modifier.fillMaxWidth().background(Color(0xFF15100D), shape)
                .border(1.dp, Color(0xFF5F4529), shape).padding(18.dp)) {
                Text("Support Questbound", color = DonationGold, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(12.dp))
                Text("Questbound grows one guide at a time. If you enjoy the app, you can support SliverzFx and help with continued development and new guides.",
                    color = DonationBody, fontSize = 16.sp)
                Spacer(Modifier.height(12.dp))
                Text("Support is entirely optional. Contributions do not unlock extra guides or features.",
                    color = DonationBody, fontSize = 16.sp)
                Spacer(Modifier.height(22.dp))
                Text("WISE", color = DonationGold, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text("@mihag25", color = DonationBody, fontSize = 16.sp)
                Spacer(Modifier.height(10.dp))
                Text("Open Wise to choose an amount and send your contribution. Payment is handled through Wise.",
                    color = DonationBody, fontSize = 14.sp)
                Spacer(Modifier.height(16.dp))
                OutlinedButton(onClick = {
                    linkError = null
                    try {
                        uriHandler.openUri(WiseSupportUrl)
                    } catch (_: IllegalArgumentException) {
                        linkError = "Could not open Wise. Open wise.com/pay/me/mihag25 in your browser."
                    } catch (_: SecurityException) {
                        linkError = "Could not open Wise. Open wise.com/pay/me/mihag25 in your browser."
                    }
                }, modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp).testTag("donations_wise"),
                    border = BorderStroke(1.dp, DonationGold)) {
                    Text("SUPPORT WITH WISE", color = DonationGold, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
                linkError?.let {
                    Spacer(Modifier.height(12.dp))
                    Text(it, color = DonationBody, fontSize = 14.sp)
                }
                Spacer(Modifier.height(10.dp))
                Text("An internet connection is required to open Wise.", color = Color(0xFF9E8B70), fontSize = 13.sp)
            }
        }
    }
}
