package dev.iwilltry42.timestrap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.gson.GsonBuilder
import dev.iwilltry42.timestrap.tasks.TaskContent
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppPreferences.init(this)
        setContentView(R.layout.activity_main)

        if (AppPreferences.isLoggedIn) {
            main_title.text = getString(R.string.main_text, AppPreferences.username)
        }

        main_button.setOnClickListener {
            fetchTasks()
        }
    }

    private fun fetchTasks() {
        requestAPIArrayWithTokenAuth(this, AppPreferences.address, "/api/tasks/", AppPreferences.token) {success, response ->
            val responseText = findViewById<TextView>(R.id.response_text)
            if (success && response != null) {
                responseText.text = getString(R.string.response_field, GsonBuilder().setPrettyPrinting().create().toJson(response))
                TaskContent.ITEMS.clear()
                for (i in 0 until  response.length()) {
                    val task = response.getJSONObject(i)
                    TaskContent.ITEMS.add(i, TaskContent.TaskItem(task["id"].toString(), task["name"].toString(), task["hourly_rate"].toString(), task["url"].toString()))
                }
            } else {
                responseText.text = "Request Failed!"
            }
        }
    }


}

