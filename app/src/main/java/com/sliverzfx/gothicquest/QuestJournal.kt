package com.sliverzfx.gothicquest

internal enum class QuestStatus(val label: String) {
    NOT_STARTED("Not started"), IN_PROGRESS("In progress"), COMPLETED("Completed")
}

internal data class QuestProgress(val completed: Set<String>, val inProgress: Set<String>)

internal fun questStatus(completed: Set<String>, inProgress: Set<String>, key: String): QuestStatus = when {
    key in completed -> QuestStatus.COMPLETED
    key in inProgress -> QuestStatus.IN_PROGRESS
    else -> QuestStatus.NOT_STARTED
}

internal fun withQuestStatus(completed: Set<String>, inProgress: Set<String>, key: String,
    status: QuestStatus): QuestProgress = QuestProgress(
    if (status == QuestStatus.COMPLETED) completed + key else completed - key,
    if (status == QuestStatus.IN_PROGRESS) inProgress + key else inProgress - key
)
