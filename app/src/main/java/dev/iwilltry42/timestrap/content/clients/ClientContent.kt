package dev.iwilltry42.timestrap.content.clients

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
    data class Client(
        val id: Int,
        val url: String,
        val name: String,
        val paymentID: String?,
        val archive: Boolean,
        val totalProjects: Int,
        val totalDuration: String
    ) {
        override fun toString(): String = name
    }
}