package com.sliverzfx.gothicquest

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun GothicQuestApp() {
    var showSplash by remember { mutableStateOf(true) }
    var destination by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        delay(2200)
        showSplash = false
    }

    when {
        showSplash -> SplashScreen()
        destination == null -> HomeScreen { destination = it }
        else -> DestinationPlaceholder(
            title = destination!!,
            onBack = { destination = null }
        )
    }
}

@Composable
private fun DestinationPlaceholder(title: String, onBack: () -> Unit) {
    BackHandler(onBack = onBack)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF090706))
            .padding(28.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                color = Color(0xFFD6B06A),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Quest Guide — coming next",
                color = Color(0xFFC7B89B),
                fontSize = 16.sp
            )
            Text(
                text = "Use Android Back to return to the menu.",
                color = Color(0xFF8F806A),
                fontSize = 12.sp
            )
        }
    }
}
