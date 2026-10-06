package com.sliverzfx.gothicquest

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun QuestboundBrand(modifier: Modifier = Modifier, headingSize: Float = 40f) {
    Column(modifier.testTag("questbound_brand"), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("QUESTBOUND", color = Color(0xFFF0CE86), fontSize = headingSize.sp,
            fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp,
            style = TextStyle(shadow = Shadow(Color.Black, blurRadius = 10f)))
        Spacer(Modifier.height(5.dp))
        Text("RPG Quest Guides", color = Color(0xFFE5D6B8), fontSize = 16.sp,
            style = TextStyle(shadow = Shadow(Color.Black, blurRadius = 6f)))
        Spacer(Modifier.height(4.dp))
        Text("by SliverzFX", color = Color(0xFFCAAA71), fontSize = 12.sp,
            style = TextStyle(shadow = Shadow(Color.Black, blurRadius = 6f)))
    }
}
