package ru.sumin.a2dolist.data.network.dto.user

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @SerializedName("email")
    val email: String?,

    @SerializedName("firstname")
    val firstname: String?,

    @SerializedName("id")
    val id: Int?,

    @SerializedName("surname")
    val surname: String?
)
