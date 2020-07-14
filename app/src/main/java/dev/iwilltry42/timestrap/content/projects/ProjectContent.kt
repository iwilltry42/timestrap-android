package dev.iwilltry42.timestrap.content.projects

import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.util.ArrayList



object ProjectContent {

    /**
     * List of tasks
     */
    val PROJECTS: MutableList<Project> = ArrayList()
    val listTypeToken = object : TypeToken<MutableList<Project>>() {}.type


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
        @SerializedName("total_entries")
        val totalEntries: Int,
        @SerializedName("total_duration")
        val totalDuration: String,
        @SerializedName("percent_done")
        val percentDone: String?
    ) {
        override fun toString(): String = name
    }
}