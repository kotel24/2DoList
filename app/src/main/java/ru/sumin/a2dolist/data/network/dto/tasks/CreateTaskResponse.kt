package ru.sumin.a2dolist.data.network.dto.tasks

import com.google.gson.annotations.SerializedName

data class CreateTaskResponse(
    @SerializedName("id")
    val id: Int?
)