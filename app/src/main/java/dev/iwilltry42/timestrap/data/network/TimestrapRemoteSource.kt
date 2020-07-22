package dev.iwilltry42.timestrap.data.network

import androidx.lifecycle.LiveData
import dev.iwilltry42.timestrap.data.network.response.TasksResponse

interface TimestrapRemoteSource {
    val remoteTasks: LiveData<TasksResponse>

    suspend fun fetchTasks()
}