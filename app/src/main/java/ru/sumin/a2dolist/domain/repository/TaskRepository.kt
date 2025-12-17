package ru.sumin.a2dolist.domain.repository

import ru.sumin.a2dolist.domain.entity.Task

interface TaskRepository {

    suspend fun getTasks(): List<Task>

    suspend fun getTaskById(id: Int): Task

    suspend fun addTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun deleteTask(id: Int)

}