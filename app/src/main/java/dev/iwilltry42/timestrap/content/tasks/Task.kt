package dev.iwilltry42.timestrap.content.tasks

import androidx.lifecycle.LiveData
import androidx.room.*
import com.google.gson.reflect.TypeToken
import java.util.ArrayList
/**
 * List of tasks
 */
val TASKS: MutableList<Task> = ArrayList()
val taskListTypeToken = object : TypeToken<MutableList<Task>>() {}.type


/**
 * Task
 * ID
 * Name
 * Rate (Hourly)
 * URL
 */
@Entity(tableName = "tasks")
class Task(
    @PrimaryKey val id: Int,
    val name: String,
    val rate: String?,
    val url: String) {
    override fun toString(): String = name
}

@Dao
interface TaskDao {
    @Query("SELECT * from tasks ORDER BY id ASC")
    fun getTasksByIDAsc(): LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun replaceList(vararg tasks: Task)

    @Query("DELETE FROM tasks")
    suspend fun deleteAll()
}





