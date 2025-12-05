package ru.sumin.a2dolist.data.network.dto.user

import com.google.gson.annotations.SerializedName

data class UserDeleteResponse (
    @SerializedName("id")
    val id: Int?,
)