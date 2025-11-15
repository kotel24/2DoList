package ru.sumin.a2dolist.domain.usecase

import ru.sumin.a2dolist.domain.repository.LoginRepository
import javax.inject.Inject

/**
 * Юзкейс для регистрации пользователя.
 */
class RegisterUserUseCase @Inject constructor(
    private val repository: LoginRepository,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) {

    suspend operator fun invoke(email: String, password: String, confirmPassword: String): Result<Unit> {
        // 1. Валидация email
        val emailValidationResult = validateEmailUseCase(email)
        if (emailValidationResult.isFailure) {
            return emailValidationResult
        }

        // 2. Валидация пароля
        val passwordValidationResult = validatePasswordUseCase(password)
        if (passwordValidationResult.isFailure) {
            return passwordValidationResult
        }

        // 3. Валидация совпадения паролей
        if (password != confirmPassword) {
            return Result.failure(Exception("Пароли не совпадают"))
        }

        // 4. Если все в порядке, регистрируем
        return repository.register(email, password)
    }
}