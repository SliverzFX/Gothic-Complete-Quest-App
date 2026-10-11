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
    val onWise: () -> Unit = {
                    linkError = null
                    try {
                        uriHandler.openUri(WiseSupportUrl)
                    } catch (_: IllegalArgumentException) {
                        linkError = "Could not open Wise. Open wise.com/pay/me/mihag25 in your browser."
                    } catch (_: SecurityException) {
                        linkError = "Could not open Wise. Open wise.com/pay/me/mihag25 in your browser."
                    }
                }
    val onKoFi: () -> Unit = {
                    linkError = null
                    try {
                        uriHandler.openUri(KoFiSupportUrl)
                    } catch (_: IllegalArgumentException) {
                        linkError = "Could not open Ko-fi. Visit ko-fi.com/sliverzfx in your browser."
                    } catch (_: SecurityException) {
                        linkError = "Could not open Ko-fi. Visit ko-fi.com/sliverzfx in your browser."
                    }
                }
    if (isLandscapeTablet()) {
        LargeTabletDonationsPage(onBack = onBack, onWise = onWise, onKoFi = onKoFi, linkError = linkError)
        return
    }

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
                Text("CHOOSE HOW TO SUPPORT", color = DonationGold, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(12.dp))
                Text("If you'd like to support future Questbound updates, choose Wise or Ko-fi and contribute any amount. Payments are handled on their respective websites.",
                    color = DonationBody, fontSize = 14.sp, lineHeight = 21.sp)
                Spacer(Modifier.height(18.dp))
                OutlinedButton(onClick = onWise, modifier = Modifier.fillMaxWidth().heightIn(min = 64.dp).testTag("donations_wise"),
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
                Spacer(Modifier.height(12.dp))
                OutlinedButton(onClick = onKoFi, modifier = Modifier.fillMaxWidth().heightIn(min = 64.dp).testTag("donations_kofi"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = DonationGold.copy(alpha = 0.1f)),
                    border = BorderStroke(1.dp, DonationGold)) {
                    Text("SUPPORT ON", color = DonationGold, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(12.dp))
                    Image(painterResource(R.drawable.kofi_cup_icon), contentDescription = "Ko-fi",
                        modifier = Modifier.width(34.dp).height(30.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("KO-FI", color = DonationGold, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
                linkError?.let {
                    Spacer(Modifier.height(12.dp))
                    Text(it, color = DonationBody, fontSize = 14.sp)
                }
                Spacer(Modifier.height(12.dp))
                Text("An internet connection is required to open Wise or Ko-fi.", color = Color(0xFF9E8B70), fontSize = 13.sp,
                    lineHeight = 19.sp)
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

@Composable
private fun LargeTabletDonationsPage(
    onBack: () -> Unit,
    onWise: () -> Unit,
    onKoFi: () -> Unit,
    linkError: String?
) {
    val compact = isCompactLandscapeTablet()
    val cardShape = RoundedCornerShape(12.dp)
    Column(Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding()
        .padding(horizontal = if (compact) 14.dp else 32.dp).testTag("donations_screen")) {
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
        Text("DONATIONS", color = DonationGold, fontSize = if (compact) 23.sp else 28.sp, fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp, modifier = Modifier.padding(top = if (compact) 3.dp else 12.dp))
        Text("Help the next adventure take shape.", color = DonationBody, fontSize = if (compact) 13.sp else 15.sp)
        LargeTabletPageColumns(
            left = {
                Column(Modifier.fillMaxWidth()
                    .background(Brush.verticalGradient(listOf(Color(0xF5241B13), Color(0xF5120F0C))), cardShape)
                    .border(1.dp, DonationGold.copy(alpha = 0.45f), cardShape)
                    .padding(if (compact) 14.dp else 24.dp)) {
                    Text("SUPPORT QUESTBOUND", color = DonationGold, fontSize = if (compact) 19.sp else 23.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(6.dp))
                    Text("BY SLIVERZFX", color = Color(0xFF9E8B70), fontSize = 12.sp, letterSpacing = 1.5.sp)
                    Spacer(Modifier.height(20.dp))
                    Text("Questbound grows one guide at a time. Your support helps with continued development and new guides.",
                        color = DonationBody, fontSize = if (compact) 15.sp else 17.sp,
                        lineHeight = if (compact) 21.sp else 25.sp)
                    Spacer(Modifier.height(18.dp))
                    HorizontalDivider(color = DonationGold.copy(alpha = 0.25f))
                    Spacer(Modifier.height(18.dp))
                    Text("EVERY ADVENTURE STAYS FREE", color = DonationGold, fontSize = 16.sp,
                        fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Text("Donations are entirely optional. All guides, tools and features remain available without contributing.",
                        color = DonationBody, fontSize = 15.sp, lineHeight = 22.sp)
                    Spacer(Modifier.height(18.dp))
                    Text("Thank you for being part of Questbound.", color = DonationGold, fontSize = 14.sp)
                }
            },
            right = {
                Column(Modifier.fillMaxWidth().background(Color(0xF515100D), cardShape)
                    .border(1.dp, DonationGold.copy(alpha = 0.45f), cardShape)
                    .padding(if (compact) 14.dp else 24.dp)) {
                    Text("CHOOSE HOW TO SUPPORT", color = DonationGold,
                        fontSize = if (compact) 16.sp else 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(12.dp))
                    Text("Contribute any amount using Wise or Ko-fi. Payments are handled by their respective websites.",
                        color = DonationBody, fontSize = 15.sp, lineHeight = 22.sp)
                    Spacer(Modifier.height(if (compact) 12.dp else 24.dp))
                    OutlinedButton(onClick = onWise, modifier = Modifier.fillMaxWidth()
                        .heightIn(min = if (compact) 52.dp else 64.dp).testTag("donations_wise"),
                        shape = RoundedCornerShape(10.dp),
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
                    Spacer(Modifier.height(if (compact) 10.dp else 16.dp))
                    OutlinedButton(onClick = onKoFi, modifier = Modifier.fillMaxWidth()
                        .heightIn(min = if (compact) 52.dp else 64.dp).testTag("donations_kofi"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = DonationGold.copy(alpha = 0.1f)),
                        border = BorderStroke(1.dp, DonationGold)) {
                        Text("SUPPORT ON", color = DonationGold, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.width(12.dp))
                        Image(painterResource(R.drawable.kofi_cup_icon), contentDescription = "Ko-fi",
                            modifier = Modifier.width(34.dp).height(30.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("KO-FI", color = DonationGold, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                    linkError?.let {
                        Spacer(Modifier.height(14.dp))
                        Text(it, color = DonationBody, fontSize = 14.sp)
                    }
                    Spacer(Modifier.height(20.dp))
                    Text("An internet connection is required to open Wise or Ko-fi.",
                        color = Color(0xFF9E8B70), fontSize = 13.sp)
                }
            }
        )
    }
}

