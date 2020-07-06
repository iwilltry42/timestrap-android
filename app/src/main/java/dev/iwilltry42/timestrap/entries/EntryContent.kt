package dev.iwilltry42.timestrap.entries

import java.net.URL
import java.time.Duration
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
        val url: URL,
        val project: URL,
        val task: URL,
        val user: String,
        val date: Date,
        val duration: Duration,
        val datetimeStart: String,
        val datetimeEnd: String,
        val note: String?
    ) {
        override fun toString(): String = id.toString()
    }
}