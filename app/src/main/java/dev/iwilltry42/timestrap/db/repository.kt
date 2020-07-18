package dev.iwilltry42.timestrap.db

import androidx.lifecycle.LiveData
import dev.iwilltry42.timestrap.content.tasks.Task
import dev.iwilltry42.timestrap.content.tasks.TaskDao

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class TimestrapRepository(
    private val taskDao: TaskDao
) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allTasks: LiveData<List<Task>> = taskDao.getTasksByIDAsc()

    suspend fun insert(task: Task) {
        taskDao.insert(task)
    }

    suspend fun replaceList(vararg tasks: Task) {
        taskDao.replaceList(*tasks)
    }
}