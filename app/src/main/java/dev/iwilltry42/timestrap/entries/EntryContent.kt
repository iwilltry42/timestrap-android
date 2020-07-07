package dev.iwilltry42.timestrap.entries

import android.util.Log
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*
import kotlin.collections.ArrayList

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
        val date: Date?,
        val duration: String,
        val datetimeStart: Date?,
        val datetimeEnd: Date?,
        val note: String?
    ) {
        override fun toString(): String = id.toString()
    }
}

fun formatDate(d: String): Date?{
    val formats = arrayListOf<String>(
        "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX",
        "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX",
        "yyyy-MM-dd'T'HH:mm:ss.SSSX",
        "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
        "yyyy-MM-dd'T'HH:mm:ssXXX",
        "yyyy-MM-dd'T'HH:mm:ssX"
    )

    var date: Date? = null
    try {
        date = SimpleDateFormat().parse(d)
    } catch (e: Exception) {
        Log.d("DateParsing", "Failed to parse '$d' with default config, trying special formats...")
        for (format in formats) {
            try {
                date = SimpleDateFormat(format).parse(d)
                Log.d("DateParsing", "Successfully parsed date '$d' with format '$format'")
                break
            } catch (e: Exception) {
                continue
            }
        }
    }
    return date
}