package ru.sumin.a2dolist.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.sumin.a2dolist.domain.repository.LoginRepository
import javax.inject.Inject

/**
 * Юзкейс для наблюдения за статусом аутентификации (токеном).
 * Возвращает Flow, который будет присылать обновления.
 */
class ObserveAuthTokenUseCase @Inject constructor(
    private val repository: LoginRepository
) {

    operator fun invoke(): Flow<String?> {
        return repository.observeAuthToken()
    }
}