package ru.sumin.a2dolist.domain.usecase.auth

import kotlinx.coroutines.flow.Flow
import ru.sumin.a2dolist.domain.repository.AuthRepository

/**
 * Юзкейс для наблюдения за статусом аутентификации (токеном).
 * Возвращает Flow, который будет присылать обновления.
 */
class ObserveAuthTokenUseCase(
    private val repository: AuthRepository
) {

    operator fun invoke(): Flow<Boolean> {
        return repository.observeAuthToken()
    }
}