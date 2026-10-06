package com.sliverzfx.gothicquest

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val LibraryGold = Color(0xFFC79A55)
private val LibraryGoldDark = Color(0xFF765225)

data class GameLibraryEntry(
    val id: String,
    val title: String,
    val panelRes: Int,
    val logoRes: Int?,
    val testTag: String,
    val onClick: () -> Unit
)

@Composable
fun GameLibraryScreen(
    title: String,
    entries: List<GameLibraryEntry>,
    onBack: () -> Unit,
    topRightActionLabel: String? = null,
    onTopRightAction: (() -> Unit)? = null,
    enableGameSearch: Boolean = false
) {
    var searchVisible by rememberSaveable { mutableStateOf(false) }
    var query by rememberSaveable { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current
    val listState = rememberLazyListState()
    val closeSearch: () -> Unit = {
        searchVisible = false
        query = ""
        focusManager.clearFocus()
        keyboard?.hide()
    }
    BackHandler {
        if (searchVisible) closeSearch() else onBack()
    }
    LaunchedEffect(searchVisible) {
        if (searchVisible) focusRequester.requestFocus()
    }
    LaunchedEffect(query) { listState.scrollToItem(0) }
    val filteredEntries = if (!enableGameSearch || query.isBlank()) entries else {
        val search = query.trim().lowercase()
            .replace(Regex("\\biii\\b"), "3")
            .replace(Regex("\\bii\\b"), "2")
            .replace(Regex("\\bi\\b"), "1")
            .replace(Regex("\\s+"), "")
        entries.filter { entry ->
            val gameName = entry.title.lowercase()
                .replace(Regex("\\biii\\b"), "3")
                .replace(Regex("\\bii\\b"), "2")
                .replace(Regex("\\bi\\b"), "1")
                .replace(Regex("\\s+"), "")
            gameName.contains(search) || entry.id.lowercase().replace("_", "").contains(search) ||
                (entry.id == "gothic" && "gothic1".contains(search))
        }
    }

    Column(Modifier.fillMaxSize().background(Color.Black)
        .statusBarsPadding().navigationBarsPadding().imePadding()
        .testTag("game_library_screen")) {
        Row(Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            TextButton(onClick = onBack, modifier = Modifier.heightIn(min = 56.dp).testTag("library_back")) {
                Text("‹  BACK", color = Color(0xFFD3B071), fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold)
            }
            if (enableGameSearch) {
                TextButton(onClick = {
                    if (searchVisible) closeSearch() else searchVisible = true
                }, modifier = Modifier.heightIn(min = 56.dp).testTag("library_search_toggle")) {
                    Text(if (searchVisible) "CLOSE" else "SEARCH",
                        color = Color(0xFFD3B071), fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold)
                }
            }
            if (topRightActionLabel != null && onTopRightAction != null) {
                TextButton(onClick = onTopRightAction,
                    modifier = Modifier.heightIn(min = 56.dp).testTag("library_top_right_action")) {
                    Text(topRightActionLabel, color = Color(0xFFD3B071), fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold)
                }
            }
        }
        if (enableGameSearch && searchVisible) {
            OutlinedTextField(value = query, onValueChange = { query = it }, singleLine = true,
                label = { Text("Search games") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
                    .focusRequester(focusRequester).testTag("library_game_search"),
                trailingIcon = {
                    if (query.isNotEmpty()) TextButton(onClick = { query = "" },
                        modifier = Modifier.testTag("library_clear_search")) {
                        Text("CLEAR", color = LibraryGold, fontSize = 12.sp)
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    focusManager.clearFocus()
                    keyboard?.hide()
                }),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color(0xFFE5D5B5), unfocusedTextColor = Color(0xFFE5D5B5),
                    focusedBorderColor = LibraryGold, unfocusedBorderColor = LibraryGoldDark,
                    focusedLabelColor = LibraryGold, unfocusedLabelColor = LibraryGold,
                    cursorColor = LibraryGold))
        }
        GoldDivider(Modifier.testTag("library_header_divider"))
        // Separate clipped viewport: cards cannot draw over the fixed header or divider.
        LazyColumn(state = listState,
            modifier = Modifier.weight(1f).fillMaxWidth().clipToBounds().testTag("library_game_list")) {
            if (filteredEntries.isEmpty()) {
                item {
                    Box(Modifier.fillMaxWidth().height(180.dp), contentAlignment = Alignment.Center) {
                        Text(if (entries.isEmpty()) title else "No games found.",
                            color = Color(0xFFB6935B), fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold)
                    }
                }
            } else {
                itemsIndexed(filteredEntries, key = { _, entry -> entry.id }) { index, entry ->
                    GameLibraryPanel(entry)
                    if (index != filteredEntries.lastIndex) GoldDivider()
                }
            }
        }
    }
}

@Composable
private fun GameLibraryPanel(entry: GameLibraryEntry) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val reduceAnimations = LocalReduceAnimations.current
    val panelHeight by animateDpAsState(
        targetValue = if (pressed && !reduceAnimations) 192.5.dp else 154.dp,
        animationSpec = tween(if (reduceAnimations) 0 else 180),
        label = "gamePanelHeight"
    )
    val logoWidth by animateDpAsState(
        targetValue = if (pressed && !reduceAnimations) 210.dp else 190.dp,
        animationSpec = tween(if (reduceAnimations) 0 else 180),
        label = "gameLogoWidth"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(panelHeight)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = entry.onClick
            )
            .testTag(entry.testTag),
        contentAlignment = Alignment.BottomEnd
    ) {
        Image(
            painter = painterResource(entry.panelRes),
            contentDescription = entry.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0x18000000), Color.Transparent, Color(0x59000000))
                    )
                )
        )
        entry.logoRes?.let { logo ->
            Image(
                painter = painterResource(logo),
                contentDescription = null,
                modifier = Modifier
                    .width(logoWidth)
                    .padding(end = 14.dp, bottom = 12.dp)
                    .offset(x = 5.dp, y = 6.dp)
                    .blur(6.dp)
                    .alpha(0.68f),
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(Color.Black)
            )
            Image(
                painter = painterResource(logo),
                contentDescription = null,
                modifier = Modifier
                    .width(logoWidth)
                    .padding(end = 14.dp, bottom = 12.dp)
                    .offset(x = 2.dp, y = 3.dp)
                    .blur(2.5.dp)
                    .alpha(0.52f),
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(Color.Black)
            )
            Image(
                painter = painterResource(logo),
                contentDescription = null,
                modifier = Modifier
                    .width(logoWidth)
                    .padding(end = 14.dp, bottom = 12.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
private fun GoldDivider(modifier: Modifier = Modifier) {
    Box(
        modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(
                Brush.horizontalGradient(
                    listOf(
                        Color.Transparent,
                        LibraryGoldDark,
                        LibraryGold,
                        LibraryGoldDark,
                        Color.Transparent
                    )
                )
            )
    )
}
