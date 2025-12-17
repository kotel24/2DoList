package ru.sumin.a2dolist.domain.usecase.task

import ru.sumin.a2dolist.domain.repository.TaskRepository
import ru.sumin.a2dolist.domain.entity.Task

class EditTaskUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(task: Task) {
        // Та же валидация, что и при создании
        if (task.title.isBlank()) {
            throw IllegalArgumentException("Название задачи не может быть пустым")
        }
        repository.updateTask(task)
    }
}