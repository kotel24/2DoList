package ru.sumin.a2dolist.data.network.dto.user

import com.google.gson.annotations.SerializedName

data class UserChangeResponse(

    @SerializedName("email")
    val email: String?,

    @SerializedName("firstname")
    val firstname: String?,

    @SerializedName("surname")
    val surname: String?
)
