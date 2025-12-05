package ru.sumin.a2dolist.data.network.dto.sub_tasks

import com.google.gson.annotations.SerializedName

data class CreateSubTaskRequest(
    @SerializedName("title")
    val title: String
)
