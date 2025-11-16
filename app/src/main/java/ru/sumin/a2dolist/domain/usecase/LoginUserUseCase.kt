package ru.sumin.a2dolist.domain.usecase

import ru.sumin.a2dolist.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repository: LoginRepository,
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