package dev.iwilltry42.timestrap.content.projects

import java.util.ArrayList

object ProjectContent {

    /**
     * List of tasks
     */
    val PROJECTS: MutableList<Project> = ArrayList()


    /**
     * Task
     * ID
     * Name
     * Rate (Hourly)
     * URL
     */
    data class Project(
        val id: Int,
        val url: String,
        val client: String,
        val name: String,
        val archive: Boolean,
        val estimate: String?,
        val totalEntries: Int,
        val totalDuration: String,
        val percentDone: String?
    ) {
        override fun toString(): String = name
    }
}