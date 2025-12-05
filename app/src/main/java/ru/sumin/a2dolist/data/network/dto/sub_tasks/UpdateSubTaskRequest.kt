package ru.sumin.a2dolist.data.network.dto.sub_tasks

import com.google.gson.annotations.SerializedName

data class UpdateSubTaskRequest (

    @SerializedName("is_complete")
    val isComplete: Boolean?,

    @SerializedName("title")
    val title: String?,

)