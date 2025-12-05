package ru.sumin.a2dolist.data.network.dto.authorization

import com.google.gson.annotations.SerializedName

data class ValidateCodeResponse(

    @SerializedName("message")
    val message: String?,

)
