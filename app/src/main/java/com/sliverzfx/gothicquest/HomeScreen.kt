package com.sliverzfx.gothicquest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen() {
    Box(
        Modifier.fillMaxSize().background(Color(0xFF090706)).testTag("home_screen"),
        contentAlignment = Alignment.Center
    ) {
        Text("Gothic", color = Color(0xFFD6B06A))
    }
}
