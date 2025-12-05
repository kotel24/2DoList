package ru.sumin.a2dolist.data.network.dto.sub_tasks

import com.google.gson.annotations.SerializedName

data class CreateSubTaskResponse(
    @SerializedName("id")
    val id: Int?
)
