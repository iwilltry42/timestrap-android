package dev.iwilltry42.timestrap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import dev.iwilltry42.timestrap.tasks.TaskContent
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppPreferences.init(this)
        setContentView(R.layout.activity_main)

        // Custom Welcome if User is logged in
        if (AppPreferences.isLoggedIn) {
            main_title.text = getString(R.string.main_text, AppPreferences.username)
        }

        main_button.setOnClickListener {
            fetchTasks()
        }

        app_bar_button_tasks.setOnClickListener {
            fetchTasks()
        }
    }

    // fetch and display the list of existing tasks from the Timestrap Server
    private fun fetchTasks() {
        requestAPIArrayWithTokenAuth(this, AppPreferences.address, "/api/tasks/", AppPreferences.token) {success, response ->
            if (success && response != null) {
                TaskContent.TASKS.clear()
                for (i in 0 until  response.length()) {
                    val task = response.getJSONObject(i)
                    TaskContent.TASKS.add(i, TaskContent.Task(task["id"].toString(), task["name"].toString(), task["hourly_rate"].toString(), task["url"].toString()))
                }
                Log.d("Tasks", "$TaskContent.ITEMS")
                Toast.makeText(this, "Fetched ${TaskContent.TASKS.size} tasks", Toast.LENGTH_SHORT).show()
                findViewById<RecyclerView>(R.id.list).adapter?.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Request Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }


}

