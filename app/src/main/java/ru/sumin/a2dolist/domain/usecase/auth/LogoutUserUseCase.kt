package ru.sumin.a2dolist.domain.usecase.auth

import ru.sumin.a2dolist.domain.repository.AuthRepository

/**
 * Юзкейс для выхода пользователя из системы.
 */
class LogoutUserUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke() {
        // Просто делегируем вызов репозиторию
        repository.logout()
    }
}