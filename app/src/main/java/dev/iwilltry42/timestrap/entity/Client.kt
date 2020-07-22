package dev.iwilltry42.timestrap.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.reflect.TypeToken
import java.util.ArrayList


/**
 * List of tasks
 */
val CLIENTS: MutableList<Client> = ArrayList()
val clientListTypeToken = object : TypeToken<MutableList<Client>>() {}.type


/**
 * Task
 * ID
 * Name
 * Rate (Hourly)
 * URL
 */
@Entity(tableName = "clients")
class Client(
    @PrimaryKey val id: Int,
    val url: String,
    val name: String,
    @ColumnInfo(name = "payment_id") val paymentID: String?,
    val archive: Boolean,
    @ColumnInfo(name = "total_projects") val totalProjects: Int,
    @ColumnInfo(name = "total_duration") val totalDuration: String
) {
    override fun toString(): String = name
}