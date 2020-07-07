package dev.iwilltry42.timestrap.entries

import java.util.*

object EntryContent {

    /**
     * List of entries
     */
    val ENTRIES: MutableList<Entry> = ArrayList()


    /**
     * Entry
     */
    data class Entry(
        val id: Int,
        val url: String,
        val project: String,
        val task: String,
        val user: String,
        val date: Date,
        val duration: String,
        val datetimeStart: String,
        val datetimeEnd: String,
        val note: String?
    ) {
        override fun toString(): String = id.toString()
    }
}