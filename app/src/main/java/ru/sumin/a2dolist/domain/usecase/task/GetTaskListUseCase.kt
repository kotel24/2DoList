package ru.sumin.a2dolist.domain.usecase.task

import ru.sumin.a2dolist.domain.repository.TaskRepository
import ru.sumin.a2dolist.domain.entity.Task

class GetTaskListUseCase(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(): List<Task> {
        return repository.getTasks()
    }
}