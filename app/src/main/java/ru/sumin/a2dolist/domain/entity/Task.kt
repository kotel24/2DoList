package ru.sumin.a2dolist.domain.entity

data class Task(
    val id: Int,

    val title: String,

    val description: String,

    val deadline: String,

    val status: String,

    val userId: Int,

    val subTasks: List<SubTask> = emptyList()
)
