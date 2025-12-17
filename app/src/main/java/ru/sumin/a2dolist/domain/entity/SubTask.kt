package ru.sumin.a2dolist.domain.entity

data class SubTask(
    val id: Int,

    val isComplete: Boolean,

    val taskId: Int,

    val title: String
)
