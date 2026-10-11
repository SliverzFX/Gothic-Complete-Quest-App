package com.sliverzfx.gothicquest

internal enum class CompletedQuestDisplay(val label: String) {
    SHOW("Show"), DIM("Dim"), HIDE("Hide");

    fun isVisible(completed: Boolean): Boolean = this != HIDE || !completed
    fun opacity(completed: Boolean): Float = if (this == DIM && completed) 0.5f else 1f

    companion object {
        fun fromStoredValue(value: String?): CompletedQuestDisplay =
            entries.firstOrNull { it.name == value } ?: SHOW
    }
}
