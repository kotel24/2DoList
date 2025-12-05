package ru.sumin.a2dolist.data.network.dto.user

import com.google.gson.annotations.SerializedName

data class UserChangeRequest(
    @SerializedName("firstname")
    val firstname: String?,

    @SerializedName("surname")
    val surname: String?
)