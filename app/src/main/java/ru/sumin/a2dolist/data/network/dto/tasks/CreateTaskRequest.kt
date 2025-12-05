package ru.sumin.a2dolist.data.network.dto.tasks

import com.google.gson.annotations.SerializedName

data class CreateTaskRequest(
    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String?,

    @SerializedName("deadline")
    val deadline: String?,

    @SerializedName("status")
    val status: String,

)
