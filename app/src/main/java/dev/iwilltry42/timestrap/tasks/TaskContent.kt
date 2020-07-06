package dev.iwilltry42.timestrap.tasks

import java.util.ArrayList

object TaskContent {

    /**
     * List of tasks
     */
    val TASKS: MutableList<Task> = ArrayList()


    /**
     * Task
     */
    data class Task(val id: String, val name: String, val rate: String?, val url: String) {
        override fun toString(): String = name
    }
}