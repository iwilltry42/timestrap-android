package dev.iwilltry42.timestrap.data.repository

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.iwilltry42.timestrap.AppPreferences
import dev.iwilltry42.timestrap.entity.Task
import dev.iwilltry42.timestrap.entity.TaskDao
import dev.iwilltry42.timestrap.requestAPIArrayWithTokenAuth

val TASK_LIST_TYPE_TOKEN = object : TypeToken<List<Task>>() {}.type

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class TimestrapRepositoryImpl(
    private val taskDao: TaskDao
) : TimestrapRepository {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allTasks: LiveData<List<Task>> = taskDao.getTasksByIDAsc()

    override suspend fun getTasks(): LiveData<Task> {
        TODO("Not yet implemented")
    }

    suspend fun insert(task: Task) {
        taskDao.insert(task)
    }

    suspend fun replaceList(vararg tasks: Task) {
        taskDao.replaceList(*tasks)
    }

    fun refreshTasks(context: Context) {
        // fetch and display the list of existing tasks from the Timestrap Server

    }
}