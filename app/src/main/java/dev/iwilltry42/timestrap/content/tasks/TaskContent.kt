package dev.iwilltry42.timestrap.content.tasks

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.reflect.TypeToken
import java.util.ArrayList

object TaskContent {

    /**
     * List of tasks
     */
    val TASKS: MutableList<Task> = ArrayList()
    val listTypeToken = object : TypeToken<MutableList<Task>>() {}.type


    /**
     * Task
     * ID
     * Name
     * Rate (Hourly)
     * URL
     */
    @Entity(tableName = "tasks")
    data class Task(
        @PrimaryKey val id: Int,
        val name: String,
        val rate: String?,
        val url: String) {
        override fun toString(): String = name
    }
}