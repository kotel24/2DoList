package ru.sumin.a2dolist.domain.usecase.auth

import ru.sumin.a2dolist.domain.repository.AuthRepository

/**
 * Юзкейс для проверки, залогинен ли пользователь в данный момент.
 */
class CheckAuthStatusUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(): Boolean {
        // Просто делегируем вызов репозиторию
        return repository.isUserLoggedIn()
    }
}