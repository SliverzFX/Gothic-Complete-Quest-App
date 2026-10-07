package com.sliverzfx.gothicquest

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun GothicReferenceScreen(section: ToolSection, onBack: () -> Unit, onHome: () -> Unit,
    embedded: Boolean = false, game: GameId = GameId.GOTHIC) {
    val gold = Color(0xFFD7B06A)
    val body = Color(0xFFC7B89B)
    var searchVisible by rememberSaveable(game, section) { mutableStateOf(false) }
    var query by rememberSaveable(game, section) { mutableStateOf("") }
    var selectedCategory by rememberSaveable(game, section) { mutableStateOf<CodeCategory?>(null) }
    var copiedId by rememberSaveable(game, section) { mutableStateOf<String?>(null) }
    var sourceError by rememberSaveable(game, section) { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current
    val closeSearch: () -> Unit = {
        searchVisible = false
        query = ""
        focusManager.clearFocus()
        keyboard?.hide()
    }
    BackHandler(enabled = !embedded || searchVisible) { if (searchVisible) closeSearch() else onBack() }
    val clipboard = LocalClipboardManager.current
    val uriHandler = LocalUriHandler.current
    val allEntries = when (game) {
        GameId.GOTHIC_2_GOLD -> Gothic2ToolsData.entries(section)
        GameId.ARCHOLOS -> ArcholosToolsData.entries(section)
        else -> GothicToolsData.entries(section)
    }
    val filtered = allEntries.filter { entry ->
        (section != ToolSection.MARVIN_CODES || selectedCategory == null || entry.codeCategory == selectedCategory) &&
            (query.isBlank() || listOf(entry.title, entry.group, entry.body, entry.command.orEmpty())
                .any { it.contains(query.trim(), ignoreCase = true) })
    }
    val listState = rememberLazyListState()
    LaunchedEffect(searchVisible) { if (searchVisible) focusRequester.requestFocus() }
    LaunchedEffect(game, query, section, selectedCategory) { listState.scrollToItem(0) }
    Column(Modifier.fillMaxSize().imePadding().then(if (embedded) Modifier else
        Modifier.statusBarsPadding().navigationBarsPadding().padding(horizontal = 20.dp))
        .testTag("tool_reference_screen")) {
        if (!embedded) {
            ToolsHeader(onBack, onHome,
                onSearch = { if (searchVisible) closeSearch() else searchVisible = true },
                searchVisible = searchVisible)
            Spacer(Modifier.height(16.dp))
        }
        Row(Modifier.fillMaxWidth()) {
            Text(section.title, color = gold, fontSize = if (embedded) 20.sp else 26.sp,
                fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f).padding(top = 12.dp))
            if (embedded) TextButton(onClick = {
                if (searchVisible) closeSearch() else searchVisible = true
            }, modifier = Modifier.heightIn(min = 48.dp).testTag("tools_search_toggle")) {
                Text(if (searchVisible) "CLOSE" else "SEARCH", color = gold, fontSize = 14.sp)
            }
        }
        if (!embedded) Text(game.persistedName, color = body, fontSize = 18.sp)
        Spacer(Modifier.height(6.dp))
        Text(if (game == GameId.ARCHOLOS) {
            "Archolos • item source v${ArcholosToolsData.sourceVersion}"
        } else if (game == GameId.GOTHIC_2_GOLD) {
            if (section == ToolSection.MARVIN_CODES) "Gold / Night of the Raven • PC Marvin mode"
            else "Gold / Night of the Raven • normal gameplay"
        } else if (section == ToolSection.MARVIN_CODES) "Original Gothic • PC Marvin mode"
            else "Original Gothic • normal gameplay", color = body, fontSize = 13.sp)
        if (section == ToolSection.MARVIN_CODES) {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth().testTag("code_category_list")) {
                item {
                    CodeCategoryChip("All Codes", selectedCategory == null, "code_category_all") {
                        selectedCategory = null
                    }
                }
                items(CodeCategory.entries.filter { category -> allEntries.any { it.codeCategory == category } }, key = { it.name }) { category ->
                    CodeCategoryChip(category.title, selectedCategory == category,
                        "code_category_${category.name.lowercase()}") { selectedCategory = category }
                }
            }
        }
        if (searchVisible) {
            OutlinedTextField(value = query, onValueChange = { query = it }, singleLine = true,
                label = { Text("Search this section") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp).focusRequester(focusRequester).testTag("tool_reference_search"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = body, unfocusedTextColor = body,
                    focusedBorderColor = gold, unfocusedBorderColor = gold.copy(alpha = 0.5f),
                    focusedLabelColor = gold, unfocusedLabelColor = body, cursorColor = gold))
        } else {
            Spacer(Modifier.height(12.dp))
        }
        LazyColumn(state = listState, modifier = Modifier.weight(1f).testTag("tool_reference_list")) {
            item {
                Text("${filtered.size} / ${allEntries.size} entries • reference", color = body, fontSize = 13.sp)
                if (game == GameId.ARCHOLOS) {
                    Spacer(Modifier.height(6.dp))
                    Text("Source snapshot v${ArcholosToolsData.sourceVersion}. Later patches may differ. " +
                        "Codes include quest, NPC and legacy objects; normal acquisition is not verified.",
                        color = body, fontSize = 13.sp, lineHeight = 18.sp)
                }
                Spacer(Modifier.height(12.dp))
            }
            items(filtered, key = { it.id }) { entry ->
                val shape = RoundedCornerShape(12.dp)
                Column(Modifier.fillMaxWidth().background(Color(0xF015100D), shape)
                    .border(1.dp, gold.copy(alpha = 0.35f), shape)
                    .testTag("gothic_reference_${entry.id}").padding(18.dp)) {
                    Text(entry.group, color = gold.copy(alpha = 0.8f), fontSize = 12.sp, letterSpacing = 1.sp)
                    Spacer(Modifier.height(6.dp))
                    Text(entry.title, color = gold, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(10.dp))
                    Text(entry.body, color = body, fontSize = 16.sp, lineHeight = 23.sp)
                    entry.command?.let { command ->
                        Spacer(Modifier.height(12.dp))
                        Row(Modifier.fillMaxWidth()) {
                            Text(command, color = gold, fontSize = 15.sp, fontFamily = FontFamily.Monospace,
                                modifier = Modifier.weight(1f).padding(top = 16.dp))
                            TextButton(onClick = {
                                clipboard.setText(AnnotatedString(command))
                                copiedId = entry.id
                            }, modifier = Modifier.heightIn(min = 56.dp).testTag("tool_copy_${entry.id}")) {
                                Text(if (copiedId == entry.id) "COPIED" else "COPY", color = gold, fontSize = 14.sp)
                            }
                        }
                    }
                }
                Spacer(Modifier.height(12.dp))
            }
            if (filtered.isEmpty()) item {
                Text("No matching entries. Try another search.", color = body, fontSize = 16.sp)
                Spacer(Modifier.height(16.dp))
            }
            item {
                Text("REFERENCE SOURCES", color = gold, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                allEntries.map { it.source }.distinct().forEachIndexed { index, source ->
                    TextButton(onClick = {
                        sourceError = false
                        try { uriHandler.openUri(source) }
                        catch (_: IllegalArgumentException) { sourceError = true }
                        catch (_: SecurityException) { sourceError = true }
                    }, modifier = Modifier.heightIn(min = 56.dp)) {
                        Text("${index + 1}. ${when {
                                source.contains("github.com/auronen/Gothic-1-localization") ||
                                    source.contains("github.com/auronen/Gothic-2-localization") -> "Gothic script reference"
                                source.contains("github.com/auronen/CoM-itemlist") -> "Archolos item export v1.2.2"
                                source.contains("gothicz.net") -> "Gothicz.net"
                                source.contains("gamefaqs.gamespot.com") -> "GameFAQs"
                                else -> "World of Gothic"
                            }} • ${allEntries.first { it.source == source }.group.lowercase()}",
                            color = gold, fontSize = 14.sp)
                    }
                }
                if (sourceError) Text("Could not open the reference website.", color = body, fontSize = 14.sp)
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun CodeCategoryChip(label: String, selected: Boolean, tag: String, onClick: () -> Unit) {
    val gold = Color(0xFFD7B06A)
    FilterChip(selected = selected, onClick = onClick,
        label = { Text(label, fontSize = 14.sp) },
        modifier = Modifier.heightIn(min = 48.dp).testTag(tag),
        colors = FilterChipDefaults.filterChipColors(
            containerColor = Color(0xE615100D), labelColor = gold,
            selectedContainerColor = Color(0xFF594123), selectedLabelColor = Color(0xFFFFE0A0)))
}
