package dev.iwilltry42.timestrap.tasks

import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object TaskContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<TaskItem> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    private val ITEM_MAP: MutableMap<String, TaskItem> = HashMap()

    private const val COUNT = 25

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createTaskItem(i))
        }
    }

    private fun addItem(item: TaskItem) {
        ITEMS.add(item)
        ITEM_MAP[item.id] = item
    }

    private fun createTaskItem(position: Int): TaskItem {
        return TaskItem(position.toString(), "Item $position", makeDetails(position))
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0 until position) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

    /**
     * A dummy item representing a piece of content.
     */
    data class TaskItem(val id: String, val content: String, val details: String) {
        override fun toString(): String = content
    }
}