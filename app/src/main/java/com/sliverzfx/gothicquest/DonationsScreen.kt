package com.sliverzfx.gothicquest

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DonationGold = Color(0xFFD7B06A)
private val DonationBody = Color(0xFFC7B89B)
private const val WiseSupportUrl = "https://wise.com/pay/me/mihag25"
private const val KoFiSupportUrl = "https://ko-fi.com/sliverzfx"

@Composable
internal fun DonationsScreen(onBack: () -> Unit) {
    BackHandler(onBack = onBack)
    val uriHandler = LocalUriHandler.current
    var linkError by remember { mutableStateOf<String?>(null) }
    val shape = RoundedCornerShape(16.dp)

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
        HorizontalDivider(color = DonationGold.copy(alpha = 0.35f))
        Column(Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(top = 24.dp, bottom = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text("DONATIONS", color = DonationGold, fontSize = 28.sp, fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp, textAlign = TextAlign.Center)
            Spacer(Modifier.height(8.dp))
            Text("Help the next adventure take shape.", color = DonationBody, fontSize = 14.sp,
                textAlign = TextAlign.Center)
            Spacer(Modifier.height(24.dp))
            Column(Modifier.fillMaxWidth().background(
                Brush.verticalGradient(listOf(Color(0xF5241B13), Color(0xF5120F0C))), shape)
                .border(1.dp, DonationGold.copy(alpha = 0.45f), shape).padding(20.dp)) {
                Text("Support Questbound", color = DonationGold, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(6.dp))
                Text("BY SLIVERZFX", color = Color(0xFF9E8B70), fontSize = 12.sp, letterSpacing = 1.5.sp)
                Spacer(Modifier.height(18.dp))
                Text("Questbound grows one guide at a time. Your support helps with continued development and new guides.",
                    color = DonationBody, fontSize = 16.sp, lineHeight = 23.sp)
                Spacer(Modifier.height(20.dp))
                HorizontalDivider(color = DonationGold.copy(alpha = 0.22f))
                Spacer(Modifier.height(20.dp))
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("SUPPORT VIA WISE", color = DonationGold, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Text("@mihag25", color = DonationBody, fontSize = 14.sp)
                }
                Spacer(Modifier.height(12.dp))
                Text("Choose any amount on Wise. Payment is handled through Wise.",
                    color = DonationBody, fontSize = 14.sp, lineHeight = 21.sp)
                Spacer(Modifier.height(18.dp))
                OutlinedButton(onClick = {
                    linkError = null
                    try {
                        uriHandler.openUri(WiseSupportUrl)
                    } catch (_: IllegalArgumentException) {
                        linkError = "Could not open Wise. Open wise.com/pay/me/mihag25 in your browser."
                    } catch (_: SecurityException) {
                        linkError = "Could not open Wise. Open wise.com/pay/me/mihag25 in your browser."
                    }
                }, modifier = Modifier.fillMaxWidth().heightIn(min = 64.dp).testTag("donations_wise"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = DonationGold.copy(alpha = 0.1f)),
                    border = BorderStroke(1.dp, DonationGold)) {
                    Text("SUPPORT WITH", color = DonationGold, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(12.dp))
                    Box(Modifier.background(Color(0xFF9FE870), RoundedCornerShape(5.dp))
                        .padding(horizontal = 8.dp, vertical = 7.dp), contentAlignment = Alignment.Center) {
                        Image(painterResource(R.drawable.wise_logo), contentDescription = "Wise",
                            modifier = Modifier.width(62.dp).height(15.dp))
                    }
                }
                linkError?.let {
                    Spacer(Modifier.height(12.dp))
                    Text(it, color = DonationBody, fontSize = 14.sp)
                }
                Spacer(Modifier.height(12.dp))
                Text("An internet connection is required to open Wise.", color = Color(0xFF9E8B70), fontSize = 13.sp,
                    lineHeight = 19.sp)
                Spacer(Modifier.height(22.dp))
                HorizontalDivider(color = DonationGold.copy(alpha = 0.22f))
                Spacer(Modifier.height(20.dp))
                Text("SUPPORT VIA KO-FI", color = DonationGold, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(12.dp))
                Text("Choose any amount on Ko-fi. Payment is handled on Ko-fi's website.",
                    color = DonationBody, fontSize = 14.sp, lineHeight = 21.sp)
                Spacer(Modifier.height(18.dp))
                OutlinedButton(onClick = {
                    linkError = null
                    try {
                        uriHandler.openUri(KoFiSupportUrl)
                    } catch (_: IllegalArgumentException) {
                        linkError = "Could not open Ko-fi. Visit ko-fi.com/sliverzfx in your browser."
                    } catch (_: SecurityException) {
                        linkError = "Could not open Ko-fi. Visit ko-fi.com/sliverzfx in your browser."
                    }
                }, modifier = Modifier.fillMaxWidth().heightIn(min = 64.dp).testTag("donations_kofi"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = DonationGold.copy(alpha = 0.1f)),
                    border = BorderStroke(1.dp, DonationGold)) {
                    Text("SUPPORT ON KO-FI", color = DonationGold, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(12.dp))
                Text("An internet connection is required to open Ko-fi.", color = Color(0xFF9E8B70), fontSize = 13.sp)
                Spacer(Modifier.height(22.dp))
                Text("Support is entirely optional. All guides and features remain available without contributing.",
                    color = DonationBody, fontSize = 14.sp, lineHeight = 21.sp)
            }
            Spacer(Modifier.height(22.dp))
            Text("Thank you for being part of Questbound.", color = DonationGold, fontSize = 14.sp,
                textAlign = TextAlign.Center)
        }
    }
}
