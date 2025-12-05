package ru.sumin.a2dolist.data.network.dto.authorization

import com.google.gson.annotations.SerializedName

data class ValidateCodeRequest(

    @SerializedName("code")
    val code: String,

    @SerializedName("email")
    val email: String
)
