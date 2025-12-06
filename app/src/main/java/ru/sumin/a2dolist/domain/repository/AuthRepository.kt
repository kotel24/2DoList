package ru.sumin.a2dolist.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(email: String, password: String): Result<Unit>

    suspend fun register(email: String, password: String): Result<Unit>

    fun observeAuthToken(): Flow<Boolean>

    suspend fun isUserLoggedIn(): Boolean

    suspend fun logout()

    suspend fun sendEmail(email: String): Result<Unit>

    suspend fun forgotPassword(email: String): Result<Unit>

    suspend fun validateEmail(code: String, email: String): Result<Unit>
}