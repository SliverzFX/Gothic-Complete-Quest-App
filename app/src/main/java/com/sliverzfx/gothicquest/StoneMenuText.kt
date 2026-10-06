package com.sliverzfx.gothicquest

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

/** Stone grain is cached per text size and masked to the letters, without bitmap assets. */
@Composable
internal fun StoneMenuText(text: String, pressed: Boolean) {
    val stoneBrush = Brush.verticalGradient(
        if (pressed) listOf(Color(0xFFFFE5A2), Color(0xFFD0CBC1), Color(0xFFAE8D50), Color(0xFFF0D393))
        else listOf(Color(0xFFD8D6CF), Color(0xFFC1C1BE), Color(0xFF8B8C88), Color(0xFFBDA36E), Color(0xFFD1D0C8))
    )
    Text(text = text, fontSize = 25.sp, fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Black, letterSpacing = 0.7.sp,
        modifier = Modifier.graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
            .drawWithCache {
                val random = Random(text.hashCode())
                val grain = List(260) {
                    Offset(random.nextFloat() * size.width, random.nextFloat() * size.height)
                }
                val cracks = List(14) {
                    val start = Offset(random.nextFloat() * size.width, random.nextFloat() * size.height)
                    start to Offset(start.x + (random.nextFloat() - 0.5f) * 5.dp.toPx(),
                        start.y + (2f + random.nextFloat() * 5f).dp.toPx())
                }
                onDrawWithContent {
                    drawContent()
                    grain.forEachIndexed { index, point ->
                        drawCircle(color = if (index % 3 == 0) Color(0x30F4E4BA) else Color(0x280D1114),
                            radius = 0.35.dp.toPx(), center = point, blendMode = BlendMode.SrcAtop)
                    }
                    cracks.forEach { (start, end) ->
                        drawLine(Color(0x50101213), start, end, strokeWidth = 0.55.dp.toPx(),
                            blendMode = BlendMode.SrcAtop)
                        val glint = Offset(0.6.dp.toPx(), 0f)
                        drawLine(Color(0x28D4B478), start + glint, end + glint,
                            strokeWidth = 0.4.dp.toPx(), blendMode = BlendMode.SrcAtop)
                    }
                }
            }.padding(2.dp),
        style = TextStyle(brush = stoneBrush,
            shadow = Shadow(color = if (pressed) Color(0xD06F4C16) else Color(0xE0000000),
                offset = Offset(1.5f, 2.5f), blurRadius = if (pressed) 7f else 3f)))
}
