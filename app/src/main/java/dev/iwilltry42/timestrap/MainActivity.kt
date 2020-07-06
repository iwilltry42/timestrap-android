package dev.iwilltry42.timestrap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.RecyclerView
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
            if (success && response != null) {
                TaskContent.ITEMS.clear()
                for (i in 0 until  response.length()) {
                    val task = response.getJSONObject(i)
                    TaskContent.ITEMS.add(i, TaskContent.TaskItem(task["id"].toString(), task["name"].toString(), task["hourly_rate"].toString(), task["url"].toString()))
                }
                Log.d("Tasks", "$TaskContent.ITEMS")
                Toast.makeText(this, "Fetched ${TaskContent.ITEMS.size} tasks", Toast.LENGTH_SHORT).show()
                findViewById<RecyclerView>(R.id.list).adapter?.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Request Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }


}

