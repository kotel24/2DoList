package ru.sumin.a2dolist.data.network.dto.authorization

import com.google.gson.annotations.SerializedName

data class ConfirmPasswordResponse(
    @SerializedName("message")
    val message: String?,

    @SerializedName("new_password")
    val newPassword: String?,
)
