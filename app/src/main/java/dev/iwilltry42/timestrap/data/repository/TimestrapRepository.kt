package dev.iwilltry42.timestrap.data.repository

import androidx.lifecycle.LiveData
import dev.iwilltry42.timestrap.entity.Task

interface TimestrapRepository {

    suspend fun getTasks(): LiveData<Task>
}