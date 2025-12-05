package ru.sumin.a2dolist.data.network.dto.sub_tasks

import com.google.gson.annotations.SerializedName

data class UpdateSubTaskResponse(

    @SerializedName("id")
    val id: Int?,

    @SerializedName("is_complete")
    val isComplete: Boolean?,

    @SerializedName("task_id")
    val taskId: Int?,

    @SerializedName("title")
    val title: String?

)
