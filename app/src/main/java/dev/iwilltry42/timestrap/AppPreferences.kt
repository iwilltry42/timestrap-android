package dev.iwilltry42.timestrap

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {
    private const val NAME = "Timestrap"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    // Variables
    private val IS_LOGGED_IN = Pair("is_logged_in", false)
    private val USERNAME = Pair("username", "")
    private val TOKEN = Pair("token", "")
    private val ADDRESS = Pair("address", "")

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    // inline fun to put & save new variable
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    // getters/setters
    var isLoggedIn: Boolean
        get() = preferences.getBoolean(IS_LOGGED_IN.first, IS_LOGGED_IN.second)
        set(value) = preferences.edit {
            it.putBoolean(IS_LOGGED_IN.first, value)
        }

    var username: String
        get() = preferences.getString(USERNAME.first, USERNAME.second) ?: ""
        set(value) = preferences.edit {
            it.putString(USERNAME.first, value)
        }

    var token: String
        get() = preferences.getString(TOKEN.first, TOKEN.second) ?: ""
        set(value) = preferences.edit {
            it.putString(TOKEN.first, value)
        }

    var address: String
        get() = preferences.getString(ADDRESS.first, ADDRESS.second) ?: ""
        set(value) = preferences.edit {
            it.putString(ADDRESS.first, value)
        }

}