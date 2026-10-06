package com.sliverzfx.gothicquest

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val SupportGold = Color(0xFFD7B06A)
private val SupportBody = Color(0xFFC7B89B)

@Composable
internal fun SupportScreen(onBack: () -> Unit) {
    BackHandler(onBack = onBack)
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    val version = remember(context) {
        @Suppress("DEPRECATION")
        context.packageManager.getPackageInfo(context.packageName, 0).versionName.orEmpty()
    }
    val report = remember(version) {
        """
            Questbound — Bug report
            App version: ${version.ifBlank { "Unknown" }}
            Phone: ${Build.MANUFACTURER} ${Build.MODEL}
            Android: ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})

            Game / mod and version:
            Chapter / quest or app screen:
            What I did (steps to reproduce):
            What I expected:
            What actually happened:
            Does it happen every time?
            Relevant app settings (text size, opacity, animations):

            Please attach a screenshot if it helps.
        """.trimIndent()
    }
    var message by remember { mutableStateOf<String?>(null) }

    Column(Modifier.fillMaxSize()

        .statusBarsPadding().navigationBarsPadding().padding(horizontal = 20.dp)
        .testTag("support_screen")) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            TextButton(onClick = onBack, modifier = Modifier.heightIn(min = 56.dp).testTag("support_back")) {
                Text("‹  BACK", color = SupportGold, fontSize = 16.sp)
            }
            TextButton(onClick = onBack, modifier = Modifier.heightIn(min = 56.dp).testTag("support_home")) {
                Text("HOME", color = SupportGold, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
        Column(Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(bottom = 24.dp)) {
            Text("SUPPORT / BUGS", color = SupportGold, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(20.dp))
            SupportBlock("REPORT A PROBLEM") {
                Text("Found an app bug or an incorrect quest step? Share it with SliverZFX on Discord.",
                    color = SupportBody, fontSize = 16.sp)
                Spacer(Modifier.height(10.dp))
                Text("Copy the report below, open Discord, paste it and fill in what happened. Include the game, chapter and quest name. For guide corrections, include your game or mod version and the correct information.",
                    color = SupportBody, fontSize = 14.sp)
                Spacer(Modifier.height(10.dp))
                Text("The template includes your app version, phone model and Android version. Nothing is sent automatically.",
                    color = SupportBody, fontSize = 13.sp)
                Spacer(Modifier.height(14.dp))
                SupportButton("COPY BUG REPORT", "support_copy_report") {
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
                    if (clipboard == null) {
                        message = "Clipboard unavailable. Please describe the problem directly in Discord."
                    } else {
                        try {
                            clipboard.setPrimaryClip(ClipData.newPlainText("Bug report", report))
                            message = "Report copied. Paste it into Discord and fill in the details."
                        } catch (_: SecurityException) {
                            message = "Could not copy the report. Please describe the problem directly in Discord."
                        }
                    }
                }
                Spacer(Modifier.height(8.dp))
                SupportButton("OPEN DISCORD", "support_discord") {
                    try {
                        uriHandler.openUri("https://discord.gg/evwry6hzwH")
                        message = null
                    } catch (_: IllegalArgumentException) {
                        message = "Could not open Discord. Check that a browser or Discord is available and try again."
                    } catch (_: SecurityException) {
                        message = "Could not open Discord. Please try again."
                    }
                }
                message?.let {
                    Spacer(Modifier.height(10.dp))
                    Text(it, color = SupportBody, fontSize = 14.sp)
                }
                Spacer(Modifier.height(10.dp))
                Text("Opening Discord needs an internet connection.", color = SupportBody, fontSize = 13.sp)
            }
            Spacer(Modifier.height(16.dp))
            SupportBlock("QUICK TROUBLESHOOTING") {
                SupportTip("A quest seems to be missing",
                    "Clear the search, check the chapter and set Completed quests to Show in Settings. Some quests depend on your faction, earlier choices or game version.")
                SupportTip("Text or backgrounds are hard to read",
                    "Try Normal text size, increase box opacity or reduce background brightness in Settings.")
                SupportTip("Animations feel slow",
                    "Turn on Reduce animations in Settings.")
                SupportTip("Music is silent",
                    "Check Music in Settings and your phone's media volume.")
                SupportTip("The app freezes or closes unexpectedly",
                    "Reopen the app. If it happens again, include the exact steps and screen in your report.")
            }
            Spacer(Modifier.height(16.dp))
            SupportBlock("KEEP YOUR PROGRESS SAFE") {
                Text("Use Export backup in Settings to save your progress, favorites and preferences before reinstalling or clearing app data. Keep the backup somewhere you can find again.",
                    color = SupportBody, fontSize = 14.sp)
            }
        }
    }
}

@Composable
private fun SupportBlock(title: String, content: @Composable ColumnScope.() -> Unit) {
    val shape = RoundedCornerShape(7.dp)
    Column(Modifier.fillMaxWidth().background(Color(0xFF15100D), shape)
        .border(1.dp, Color(0xFF5F4529), shape).padding(16.dp)) {
        Text(title, color = SupportGold, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))
        content()
    }
}

@Composable
private fun SupportTip(title: String, body: String) {
    Text(title, color = SupportGold, fontSize = 15.sp, fontWeight = FontWeight.Bold)
    Spacer(Modifier.height(4.dp))
    Text(body, color = SupportBody, fontSize = 14.sp)
    Spacer(Modifier.height(14.dp))
}

@Composable
private fun SupportButton(label: String, tag: String, onClick: () -> Unit) {
    OutlinedButton(onClick = onClick,
        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag(tag),
        shape = RoundedCornerShape(5.dp), border = BorderStroke(1.dp, Color(0xFF76552E))) {
        Text(label, color = SupportGold, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}
