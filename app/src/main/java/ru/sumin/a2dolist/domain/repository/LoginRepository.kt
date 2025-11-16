package ru.sumin.a2dolist.domain.repository

import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun login(email: String, password: String): Result<Unit>

    suspend fun register(email: String, password: String): Result<Unit>

    fun observeAuthToken(): Flow<String?>

    suspend fun isUserLoggedIn(): Boolean

    suspend fun logout()
}