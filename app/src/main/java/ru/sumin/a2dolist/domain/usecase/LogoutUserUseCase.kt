package ru.sumin.a2dolist.domain.usecase

import ru.sumin.a2dolist.domain.repository.LoginRepository
import javax.inject.Inject

/**
 * Юзкейс для выхода пользователя из системы.
 */
class LogoutUserUseCase @Inject constructor(
    private val repository: LoginRepository
) {

    suspend operator fun invoke() {
        // Просто делегируем вызов репозиторию
        repository.logout()
    }
}