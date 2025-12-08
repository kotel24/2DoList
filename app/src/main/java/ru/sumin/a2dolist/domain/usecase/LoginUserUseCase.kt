package ru.sumin.a2dolist.domain.usecase

import ru.sumin.a2dolist.domain.repository.AuthRepository

class LoginUserUseCase(
    private val repository: AuthRepository,
    private val validateEmailUseCase: ValidateEmailUseCase
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit>{
        // 1. Сначала валидируем email
        val emailValidationResult = validateEmailUseCase(email)
        if (emailValidationResult.isFailure) {
            return emailValidationResult // Возвращаем ошибку валидации
        }

        return repository.login(email, password)
    }
}