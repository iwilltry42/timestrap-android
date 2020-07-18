package dev.iwilltry42.timestrap.content.projects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.util.ArrayList


/**
 * List of tasks
 */
val PROJECTS: MutableList<Project> = ArrayList()
val projectListTypeToken = object : TypeToken<MutableList<Project>>() {}.type


/**
 * Task
 * ID
 * Name
 * Rate (Hourly)
 * URL
 */
@Entity(tableName = "projects")
class Project(
    @PrimaryKey val id: Int,
    val url: String,
    val client: String,
    val name: String,
    val archive: Boolean,
    val estimate: String?,
    @SerializedName("total_entries")
    @ColumnInfo(name = "total_entries") val totalEntries: Int,
    @SerializedName("total_duration")
    @ColumnInfo(name = "total_duration") val totalDuration: String,
    @SerializedName("percent_done")
    @ColumnInfo(name = "percent_done") val percentDone: String?
) {
    override fun toString(): String = name
}