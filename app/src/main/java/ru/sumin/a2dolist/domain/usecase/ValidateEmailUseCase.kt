package ru.sumin.a2dolist.domain.usecase

class ValidateEmailUseCase() {

    private val emailRegex = Regex(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    operator fun invoke(email: String): Result<Unit> {
        if (email.isBlank()) {
            return Result.failure(Exception("Email не может быть пустым"))
        }
        if (!emailRegex.matches(email)) {
            return Result.failure(Exception("Неверный формат email"))
        }
        return Result.success(Unit)
    }
}