package ru.sumin.a2dolist.data.network.dto.authorization

import com.google.gson.annotations.SerializedName

data class ConfirmPasswordRequest(

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("password_confirm")
    val passwordConfirm: String
)
