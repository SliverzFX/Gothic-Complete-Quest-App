package com.sliverzfx.gothicquest

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.time.LocalDate

@Composable
internal fun BackupSettingsSection(
    onRestored: () -> Unit,
    onResetGame: (GameId) -> Boolean
) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE)
    val scope = rememberCoroutineScope()
    var busy by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf<String?>(null) }
    var pendingRestore by remember { mutableStateOf<QuestAppBackup?>(null) }
    var resetGame by remember { mutableStateOf<GameId?>(null) }
    var exportText by remember { mutableStateOf<String?>(null) }

    val export = rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("application/json")) { uri ->
        val payload = exportText
        exportText = null
        if (uri != null && payload != null) scope.launch {
            busy = true
            try {
                withContext(Dispatchers.IO) {
                    val output = context.contentResolver.openOutputStream(uri, "wt")
                        ?: error("Could not open the backup file.")
                    output.bufferedWriter(Charsets.UTF_8).use { it.write(payload) }
                }
                message = "Backup saved."
            } catch (_: Exception) {
                message = "Could not save the backup. Please try another location."
            } finally { busy = false }
        }
    }
    val restore = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri != null) scope.launch {
            busy = true
            try {
                pendingRestore = withContext(Dispatchers.IO) {
                    val input = context.contentResolver.openInputStream(uri) ?: error("Could not open file.")
                    val bytes = input.use { stream ->
                        val output = ByteArrayOutputStream()
                        val buffer = ByteArray(8192)
                        while (output.size() <= 2_000_000) {
                            val count = stream.read(buffer)
                            if (count == -1) break
                            output.write(buffer, 0, count)
                        }
                        output.toByteArray()
                    }
                    require(bytes.size <= 2_000_000) { "Backup is too large." }
                    QuestBackupCodec.decode(bytes.toString(Charsets.UTF_8))
                }
            } catch (_: Exception) {
                message = "This file could not be restored. Select a valid app backup."
            } finally { busy = false }
        }
    }
    val shape = RoundedCornerShape(7.dp)
    val gold = Color(0xFFD7B06A)
    Column(Modifier.fillMaxWidth().background(Color(0xFF15100D), shape)
        .border(1.dp, Color(0xFF5F4529), shape).padding(16.dp)) {
        Text("BACKUP & RESTORE", color = gold, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(6.dp))
        Text("Save progress, favorites, settings and your Continue location to a file.",
            color = Color(0xFF9E8B70), fontSize = 12.sp)
        TextButton(enabled = !busy, onClick = {
            exportText = QuestBackupCodec.encode(QuestBackupCodec.capture(prefs))
            export.launch("gothic-quest-backup-${LocalDate.now()}.json")
        }, modifier = Modifier.testTag("backup_save")) { Text("SAVE BACKUP", color = gold) }
        TextButton(enabled = !busy, onClick = {
            restore.launch(arrayOf("application/json", "text/plain", "application/octet-stream"))
        }, modifier = Modifier.testTag("backup_restore")) { Text("RESTORE BACKUP", color = gold) }
        if (busy) Text("Working…", color = Color(0xFF9E8B70), fontSize = 12.sp)
        message?.let { Text(it, color = Color(0xFFC7B89B), fontSize = 13.sp,
            modifier = Modifier.testTag("backup_status")) }
    }
    Spacer(Modifier.height(16.dp))
    Column(Modifier.fillMaxWidth().background(Color(0xFF15100D), shape)
        .border(1.dp, Color(0xFF5F4529), shape).padding(16.dp)) {
        Text("RESET GAME PROGRESS", color = gold, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(6.dp))
        Text("Clear completion marks for one game. Favorites and other games stay saved.",
            color = Color(0xFF9E8B70), fontSize = 12.sp)
        GameId.entries.forEach { game ->
            TextButton(enabled = !busy, onClick = { resetGame = game },
                modifier = Modifier.fillMaxWidth().testTag("reset_progress_${game.name.lowercase()}")) {
                Text(game.persistedName, color = gold)
            }
        }
    }
    pendingRestore?.let { backup ->
        AlertDialog(
            onDismissRequest = { if (!busy) pendingRestore = null },
            containerColor = Color(0xFF15100D),
            title = { Text("Restore backup?", color = gold) },
            text = { Text("Replace your current progress, favorites, settings and Continue location with this backup?\n\n${backup.completed.size} completed quests • ${backup.favorites.size} favorites",
                color = Color(0xFFC7B89B)) },
            confirmButton = {
                TextButton(enabled = !busy, onClick = {
                    scope.launch {
                        busy = true
                        try {
                            val saved = withContext(Dispatchers.IO) { QuestBackupCodec.restore(prefs, backup) }
                            if (saved) {
                                onRestored()
                                message = "Backup restored."
                            } else message = "Could not save restored data. Please try again."
                        } catch (_: Exception) {
                            message = "Could not restore the backup."
                        } finally { pendingRestore = null; busy = false }
                    }
                }, modifier = Modifier.testTag("backup_confirm_restore")) { Text("RESTORE", color = gold) }
            },
            dismissButton = { TextButton(enabled = !busy, onClick = { pendingRestore = null }) { Text("CANCEL", color = gold) } }
        )
    }
    resetGame?.let { game ->
        AlertDialog(
            onDismissRequest = { resetGame = null },
            containerColor = Color(0xFF15100D),
            title = { Text("Reset ${game.persistedName} progress?", color = gold) },
            text = { Text("Clear all completion marks for this game? Your favorites and other games will be kept.",
                color = Color(0xFFC7B89B)) },
            confirmButton = {
                TextButton(onClick = {
                    message = if (onResetGame(game)) "${game.persistedName} progress reset." else "Could not reset progress. Please try again."
                    resetGame = null
                }, modifier = Modifier.testTag("reset_confirm")) { Text("RESET", color = gold) }
            },
            dismissButton = { TextButton(onClick = { resetGame = null }) { Text("CANCEL", color = gold) } }
        )
    }
}
