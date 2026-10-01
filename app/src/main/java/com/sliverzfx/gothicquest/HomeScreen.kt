package com.sliverzfx.gothicquest

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background

@Composable
fun HomeScreen(onDestinationSelected: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .testTag("home_screen"),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(R.drawable.gothic_home_menu_v1),
                contentDescription = "Gothic Complete Quest Guide menu",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            MenuHitbox(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = 230.dp)
                    .size(width = 390.dp, height = 170.dp)
                    .testTag("game_gothic"),
                onClick = { onDestinationSelected("Gothic") }
            )
            MenuHitbox(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = 415.dp)
                    .size(width = 390.dp, height = 170.dp)
                    .testTag("game_gothic_2"),
                onClick = { onDestinationSelected("Gothic II Gold Edition") }
            )
            MenuHitbox(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = 600.dp)
                    .size(width = 390.dp, height = 170.dp)
                    .testTag("game_new_balance"),
                onClick = { onDestinationSelected("Gothic II New Balance") }
            )

            MenuHitbox(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = 18.dp, y = (-20).dp)
                    .size(width = 120.dp, height = 92.dp)
                    .testTag("favorites"),
                onClick = { onDestinationSelected("Favorites") }
            )
            MenuHitbox(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = (-20).dp)
                    .size(width = 120.dp, height = 92.dp)
                    .testTag("settings"),
                onClick = { onDestinationSelected("Settings") }
            )
            MenuHitbox(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = (-18).dp, y = (-20).dp)
                    .size(width = 120.dp, height = 92.dp)
                    .testTag("about"),
                onClick = { onDestinationSelected("About") }
            )
        }
    }
}

@Composable
private fun MenuHitbox(modifier: Modifier, onClick: () -> Unit) {
    Box(modifier = modifier.clickable(onClick = onClick))
}
