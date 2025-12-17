package ru.sumin.a2dolist.domain.usecase.task

import ru.sumin.a2dolist.domain.repository.TaskRepository

class DeleteTaskUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.deleteTask(id)
    }
}