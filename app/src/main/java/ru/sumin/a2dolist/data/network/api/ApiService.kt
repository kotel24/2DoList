package ru.sumin.a2dolist.data.network.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import ru.sumin.a2dolist.data.network.dto.authorization.ConfirmPasswordRequest
import ru.sumin.a2dolist.data.network.dto.authorization.ConfirmPasswordResponse
import ru.sumin.a2dolist.data.network.dto.authorization.ForgotPasswordRequest
import ru.sumin.a2dolist.data.network.dto.authorization.ForgotPasswordResponse
import ru.sumin.a2dolist.data.network.dto.authorization.LoginRequest
import ru.sumin.a2dolist.data.network.dto.authorization.LoginResponse
import ru.sumin.a2dolist.data.network.dto.authorization.RegisterRequest
import ru.sumin.a2dolist.data.network.dto.authorization.RegisterResponse
import ru.sumin.a2dolist.data.network.dto.authorization.SendEmailRequest
import ru.sumin.a2dolist.data.network.dto.authorization.SendEmailResponse
import ru.sumin.a2dolist.data.network.dto.authorization.ValidateCodeRequest
import ru.sumin.a2dolist.data.network.dto.authorization.ValidateCodeResponse
import ru.sumin.a2dolist.data.network.dto.sub_tasks.CreateSubTaskRequest
import ru.sumin.a2dolist.data.network.dto.sub_tasks.CreateSubTaskResponse
import ru.sumin.a2dolist.data.network.dto.sub_tasks.DeleteSubTaskResponse
import ru.sumin.a2dolist.data.network.dto.sub_tasks.UpdateSubTaskRequest
import ru.sumin.a2dolist.data.network.dto.sub_tasks.UpdateSubTaskResponse
import ru.sumin.a2dolist.data.network.dto.tasks.CreateTaskRequest
import ru.sumin.a2dolist.data.network.dto.tasks.CreateTaskResponse
import ru.sumin.a2dolist.data.network.dto.tasks.TaskDeleteResponse
import ru.sumin.a2dolist.data.network.dto.tasks.TaskResponse
import ru.sumin.a2dolist.data.network.dto.tasks.TaskUpdateRequest
import ru.sumin.a2dolist.data.network.dto.tasks.TaskUpdateResponse
import ru.sumin.a2dolist.data.network.dto.user.UserChangeResponse
import ru.sumin.a2dolist.data.network.dto.user.UserDeleteResponse
import ru.sumin.a2dolist.data.network.dto.user.UserResponse

interface ApiService {

    // --- Authorization ---

    @POST("auth/confirm-password")
    suspend fun confirmPassword(@Body request: ConfirmPasswordRequest): Response<ConfirmPasswordResponse>

    @POST("auth/forgot-password")
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): Response<ForgotPasswordResponse>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("auth/send-email")
    suspend fun sendEmail(@Body request: SendEmailRequest): Response<SendEmailResponse>

    @POST("auth/validate-email")
    suspend fun validateCode(@Body request: ValidateCodeRequest): Response<ValidateCodeResponse>

    // --- Tasks ---

    @GET("api/tasks")
    suspend fun getTasks(): Response<List<TaskResponse>>

    @POST("api/tasks")
    suspend fun createTask(
        @Body request: CreateTaskRequest
    ): Response<CreateTaskResponse>

    @GET("api/tasks/{id}")
    suspend fun getTaskById(
        @Path("id") taskId: Int
    ): Response<TaskResponse>

    @DELETE("api/tasks/{id}")
    suspend fun deleteTask(
        @Path("id") taskId: Int
    ): Response<TaskDeleteResponse>

    @PATCH("api/tasks/{id}")
    suspend fun updateTask(
        @Path("id") taskId: Int,
        @Body request: TaskUpdateRequest
    ): Response<TaskUpdateResponse>

    @POST("api/tasks/{taskId}/subtasks")
    suspend fun createSubTask(
        @Path("taskId") taskId: Int,
        @Body request: CreateSubTaskRequest
    ): Response<CreateSubTaskResponse>

    // --- User ---

    @GET("api/user/me")
    suspend fun getCurrentUser(): Response<UserResponse>

    @DELETE("api/user/me")
    suspend fun deleteCurrentUser(): Response<UserDeleteResponse>

    @PATCH("api/user/me")
    suspend fun changeCurrentUser(): Response<UserChangeResponse>

    // --- SubTasks ---

    @DELETE("api/subtasks/{id}")
    suspend fun deleteSubTask(
        @Path("id") subTaskId: Int
    ): Response<DeleteSubTaskResponse>

    @PATCH("api/subtasks/{id}")
    suspend fun updateSubTask(
        @Path("id") subTaskId: Int,
        @Body request: UpdateSubTaskRequest
    ): Response<UpdateSubTaskResponse>
}