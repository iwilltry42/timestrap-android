package dev.iwilltry42.timestrap.content.clients

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.reflect.TypeToken
import java.util.ArrayList

object ClientContent {

    /**
     * List of tasks
     */
    val CLIENTS: MutableList<Client> = ArrayList()
    val listTypeToken = object : TypeToken<MutableList<Client>>() {}.type


    /**
     * Task
     * ID
     * Name
     * Rate (Hourly)
     * URL
     */
    @Entity(tableName = "clients")
    data class Client(
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
}