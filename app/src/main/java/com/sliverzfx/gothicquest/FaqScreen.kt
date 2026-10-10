package com.sliverzfx.gothicquest

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape

private val FaqGold = Color(0xFFD7B06A)
private val FaqBody = Color(0xFFC7B89B)

@Composable
internal fun FaqScreen(onBack: () -> Unit, onSupport: () -> Unit) {
    BackHandler(onBack = onBack)
    var expandedId by rememberSaveable { mutableStateOf<String?>(null) }
    val tablet = isLargeLandscapeTablet()

    Column(Modifier.fillMaxSize()

        .statusBarsPadding().navigationBarsPadding().padding(horizontal = if (tablet) 32.dp else 20.dp)
        .testTag("faq_screen")) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            TextButton(onClick = onBack, modifier = Modifier.heightIn(min = 56.dp).testTag("faq_back")) {
                Text("‹  BACK", color = FaqGold, fontSize = 16.sp)
            }
            TextButton(onClick = onBack, modifier = Modifier.heightIn(min = 56.dp).testTag("faq_home")) {
                Text("HOME", color = FaqGold, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(Modifier.fillMaxWidth().height(2.dp).background(FaqGold.copy(alpha = 0.55f)))
        if (tablet) {
            Text("FAQs", color = FaqGold, fontSize = 28.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 14.dp))
            Text("Find answers by topic, or open Support for further help.",
                color = FaqBody, fontSize = 15.sp, modifier = Modifier.padding(bottom = 8.dp))
            Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.widthIn(max = 1100.dp).fillMaxSize()
                        .clipToBounds().testTag("faq_list"),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(
                        top = 12.dp, bottom = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FaqContent.groups.forEach { group ->
                        item(key = "group_${group.title}", span = { GridItemSpan(maxLineSpan) }) {
                            Text(group.title, color = FaqGold, fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 12.dp, bottom = 2.dp))
                        }
                        group.entries.forEach { entry ->
                            item(key = entry.id) {
                                FaqQuestion(entry, expandedId == entry.id) {
                                    expandedId = if (expandedId == entry.id) null else entry.id
                                }
                            }
                        }
                    }
                    item(key = "support", span = { GridItemSpan(maxLineSpan) }) {
                        Row(Modifier.fillMaxWidth().padding(top = 12.dp, bottom = 18.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Still need help or found a guide error?",
                                color = FaqBody, fontSize = 16.sp)
                            TextButton(onClick = onSupport,
                                modifier = Modifier.heightIn(min = 56.dp).testTag("faq_support")) {
                                Text("OPEN SUPPORT", color = FaqGold, fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        } else {
        LazyColumn(Modifier.weight(1f).fillMaxWidth().clipToBounds().testTag("faq_list"),
            verticalArrangement = Arrangement.spacedBy(10.dp)) {
            item(key = "intro") {
                Column(Modifier.padding(top = 20.dp, bottom = 6.dp)) {
                    Text("FAQs", color = FaqGold, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Text("Questions about Questbound and its guides. Tap a question to read the answer.",
                        color = FaqBody, fontSize = 16.sp)
                }
            }
            FaqContent.groups.forEach { group ->
                item(key = "group_${group.title}") {
                    Text(group.title, color = FaqGold, fontSize = 16.sp, fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 12.dp, bottom = 2.dp))
                }
                group.entries.forEach { entry ->
                    item(key = entry.id) {
                        FaqQuestion(entry, expandedId == entry.id) {
                            expandedId = if (expandedId == entry.id) null else entry.id
                        }
                    }
                }
            }
            item(key = "support") {
                Column(Modifier.padding(top = 12.dp, bottom = 24.dp)) {
                    Text("Still need help or found a guide error?", color = FaqBody, fontSize = 16.sp)
                    TextButton(onClick = onSupport,
                        modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp).testTag("faq_support")) {
                        Text("OPEN SUPPORT", color = FaqGold, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        }

    }
}

@Composable
private fun FaqQuestion(entry: FaqEntry, expanded: Boolean, onClick: () -> Unit) {
    val shape = RoundedCornerShape(7.dp)
    val motion = if (LocalReduceAnimations.current) Modifier else Modifier.animateContentSize(tween(180))
    Column(Modifier.fillMaxWidth().background(Color(0xFF15100D), shape)
        .border(1.dp, Color(0xFF5F4529), shape).then(motion)) {
        TextButton(onClick = onClick, modifier = Modifier.fillMaxWidth().heightIn(min = 64.dp)
            .testTag("faq_question_${entry.id}")
            .semantics { stateDescription = if (expanded) "Expanded" else "Collapsed" }) {
            Row(Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Text(entry.question, color = FaqGold, fontSize = 18.sp,
                    fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Text(if (expanded) "−" else "+", color = FaqGold, fontSize = 24.sp,
                    modifier = Modifier.padding(start = 12.dp))
            }
        }
        if (expanded) {
            Text(entry.answer, color = FaqBody, fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 18.dp)
                    .testTag("faq_answer_${entry.id}"))
        }
    }
}
