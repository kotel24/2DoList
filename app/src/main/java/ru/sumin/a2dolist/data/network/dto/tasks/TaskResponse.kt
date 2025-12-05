package ru.sumin.a2dolist.data.network.dto.tasks

import com.google.gson.annotations.SerializedName
import ru.sumin.a2dolist.data.network.dto.sub_tasks.SubTaskResponse

data class TaskResponse(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("deadline")
    val deadline: String?,

    @SerializedName("status")
    val status: String?,

    @SerializedName("user_id")
    val userId: Int?,

    @SerializedName("sub_tasks")
    val subTasks: List<SubTaskResponse>?
)