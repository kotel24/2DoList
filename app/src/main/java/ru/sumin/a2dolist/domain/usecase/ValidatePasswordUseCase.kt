package ru.sumin.a2dolist.domain.usecase


class ValidatePasswordUseCase() {

    operator fun invoke(password: String): Result<Unit> {
        if (password.length < 8) {
            return Result.failure(Exception("Пароль должен быть не менее 8 символов"))
        }
        return Result.success(Unit)
    }
}