package com.sliverzfx.gothicquest

internal data class QuestCorrectionContext(
    val gameName: String,
    val sectionLabel: String,
    val sectionNumber: Int,
    val questTitle: String,
    val questId: String
)

internal fun buildSupportReport(
    appVersion: String,
    phone: String,
    androidVersion: String,
    correction: QuestCorrectionContext? = null
): String = buildString {
    appendLine(if (correction == null) "Questbound — Bug report" else "Questbound — Quest correction")
    appendLine("App version: ${appVersion.ifBlank { "Unknown" }}")
    appendLine("Phone: $phone")
    appendLine("Android: $androidVersion")
    appendLine()
    if (correction != null) {
        appendLine("Game / mod: ${correction.gameName}")
        appendLine("Game / mod version:")
        appendLine("${correction.sectionLabel}: ${correction.sectionNumber}")
        appendLine("Quest: ${correction.questTitle}")
        appendLine("Quest ID: ${correction.questId}")
        appendLine()
        appendLine("Incorrect step or information:")
        appendLine("Suggested correction:")
        appendLine("Source or how I verified it:")
    } else {
        appendLine("Game / mod and version:")
        appendLine("Chapter / quest or app screen:")
        appendLine("What I did (steps to reproduce):")
        appendLine("What I expected:")
        appendLine("What actually happened:")
        appendLine("Does it happen every time?")
        appendLine("Relevant app settings (text size, opacity, animations):")
    }
    appendLine()
    append("Please attach a screenshot if it helps.")
}
