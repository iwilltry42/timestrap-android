package dev.iwilltry42.timestrap.ui.settings

import java.util.ArrayList

object Settings {

    /**
     * List of tasks
     */
    val SETTINGS: MutableList<Setting> = ArrayList()


    /**
     * Task
     * ID
     * Name
     * Rate (Hourly)
     * URL
     */
    data class Setting(
        val name: String,
        val act: Class<*>
    ) {
        override fun toString(): String = name
    }
}