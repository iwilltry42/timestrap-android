package dev.iwilltry42.timestrap.tasks

import java.util.ArrayList

object TaskContent {

    /**
     * List of tasks
     */
    val ITEMS: MutableList<TaskItem> = ArrayList()

    /**
     * Task
     */
    data class TaskItem(val id: String, val name: String, val rate: String?, val url: String) {
        override fun toString(): String = name
    }
}