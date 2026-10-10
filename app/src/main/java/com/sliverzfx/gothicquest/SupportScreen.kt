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
internal fun SupportScreen(
    onBack: () -> Unit,
    onHome: () -> Unit = onBack,
    correction: QuestCorrectionContext? = null
) {
    BackHandler(onBack = onBack)
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    val version = remember(context) {
        @Suppress("DEPRECATION")
        context.packageManager.getPackageInfo(context.packageName, 0).versionName.orEmpty()
    }
    val report = remember(version, correction) {
        buildSupportReport(version, "${Build.MANUFACTURER} ${Build.MODEL}",
            "${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})", correction)
    }
    var message by remember(correction) { mutableStateOf<String?>(null) }

    val tablet = isLargeLandscapeTablet()
    val selectedQuestContent: @Composable () -> Unit = {
        correction?.let { quest ->
            SupportBlock("SELECTED QUEST") {
                Text("${quest.gameName} • ${quest.sectionLabel} ${quest.sectionNumber}\n${quest.questTitle}\n${quest.questId}",
                    color = SupportBody, fontSize = 15.sp,
                    modifier = Modifier.testTag("support_correction_context"))
            }
            Spacer(Modifier.height(16.dp))
        }
    }
    val reportContent: @Composable () -> Unit = {
        SupportBlock(if (correction == null) "REPORT A PROBLEM" else "REPORT A CORRECTION") {
            Text("Found an app bug or an incorrect quest step? Share it with SliverZFX on Discord.",
                color = SupportBody, fontSize = 16.sp)
            Spacer(Modifier.height(10.dp))
            Text(if (correction == null)
                "Copy the report, open Discord, paste it and fill in what happened. Include the game, chapter and quest name. For guide corrections, include your game or mod version and the correct information."
                else "The game, chapter or part, quest title and ID are already filled in. Copy the report, open Discord and paste it. Add your game or mod version, the incorrect step and your suggested correction.",
                color = SupportBody, fontSize = 14.sp)
            Spacer(Modifier.height(10.dp))
            Text("The template includes your app version, phone model and Android version. Nothing is sent automatically.",
                color = SupportBody, fontSize = 13.sp)
            Spacer(Modifier.height(14.dp))
            SupportButton(if (correction == null) "COPY BUG REPORT" else "COPY CORRECTION REPORT", "support_copy_report") {
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
                if (clipboard == null) {
                    message = "Clipboard unavailable. Please describe the problem directly in Discord."
                } else {
                    try {
                        clipboard.setPrimaryClip(ClipData.newPlainText(if (correction == null) "Bug report" else "Quest correction", report))
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
    }
    val troubleshootingContent: @Composable () -> Unit = {
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
    }
    val backupContent: @Composable () -> Unit = {
        SupportBlock("KEEP YOUR PROGRESS SAFE") {
            Text("Use Export backup in Settings to save your progress, favorites and preferences before reinstalling or clearing app data. Keep the backup somewhere you can find again.",
                color = SupportBody, fontSize = 14.sp)
        }
    }
    Column(Modifier.fillMaxSize()
        .statusBarsPadding().navigationBarsPadding()
        .padding(horizontal = if (tablet) 32.dp else 20.dp)
        .testTag("support_screen")) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            TextButton(onClick = onBack, modifier = Modifier.heightIn(min = 56.dp).testTag("support_back")) {
                Text("‹  BACK", color = SupportGold, fontSize = 16.sp)
            }
            TextButton(onClick = onHome, modifier = Modifier.heightIn(min = 56.dp).testTag("support_home")) {
                Text("HOME", color = SupportGold, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
        if (tablet) {
            Text("SUPPORT / BUGS", color = SupportGold, fontSize = 28.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp))
            LargeTabletPageColumns(
                left = {
                    selectedQuestContent()
                    reportContent()
                },
                right = {
                    troubleshootingContent()
                    Spacer(Modifier.height(16.dp))
                    backupContent()
                }
            )
        } else {
            Column(Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(bottom = 24.dp)) {
                Text("SUPPORT / BUGS", color = SupportGold, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(20.dp))
                selectedQuestContent()
                reportContent()
                Spacer(Modifier.height(16.dp))
                troubleshootingContent()
                Spacer(Modifier.height(16.dp))
                backupContent()
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
