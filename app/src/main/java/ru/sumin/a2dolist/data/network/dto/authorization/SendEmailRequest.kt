package ru.sumin.a2dolist.data.network.dto.authorization

import com.google.gson.annotations.SerializedName

data class SendEmailRequest(
    @SerializedName("email")
    val email: String
)
