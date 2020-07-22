package dev.iwilltry42.timestrap.data.network.response

import dev.iwilltry42.timestrap.entity.Task

data class TasksResponse(
    val tasks: List<Task>
)