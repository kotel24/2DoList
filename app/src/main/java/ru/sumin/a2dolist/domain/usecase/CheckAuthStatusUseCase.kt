package ru.sumin.a2dolist.domain.usecase

import ru.sumin.a2dolist.domain.repository.LoginRepository
import javax.inject.Inject

/**
 * Юзкейс для проверки, залогинен ли пользователь в данный момент.
 */
class CheckAuthStatusUseCase @Inject constructor(
    private val repository: LoginRepository
) {

    suspend operator fun invoke(): Boolean {
        // Просто делегируем вызов репозиторию
        return repository.isUserLoggedIn()
    }
}