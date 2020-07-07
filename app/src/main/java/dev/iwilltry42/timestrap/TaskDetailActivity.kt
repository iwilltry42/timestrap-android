package dev.iwilltry42.timestrap

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import dev.iwilltry42.timestrap.entries.EntryContent
import org.json.JSONArray

const val EXTRA_TASK_NAME = "dev.iwilltry42.timestrap.TASK_NAME"

class TaskDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        // Custom Welcome if User is logged in
        findViewById<TextView>(R.id.text_task_title).text = intent.getStringExtra(EXTRA_TASK_NAME)

        fetchEntries()

    }

    // fetch and display the list of existing entries from the Timestrap Server
    private fun fetchEntries() {
        requestAPIObjectWithTokenAuth(this, AppPreferences.address, "/api/entries/?project=1", AppPreferences.token) {success, response ->
            if (success && response != null) {
                val resultList: JSONArray = response.getJSONArray("results")
                EntryContent.ENTRIES.clear()
                for (i in 0 until  resultList.length()) {
                    val entry = resultList.getJSONObject(i)
                    EntryContent.ENTRIES.add(
                        i,
                        EntryContent.Entry(
                            entry["id"].toString(),
                            entry["url"].toString(),
                            entry["project"].toString(),
                            entry["task"].toString(),
                            entry["user"].toString(),
                            entry["date"].toString(),
                            entry["duration"].toString(),
                            entry["datetime_start"].toString(),
                            entry["datetime_end"].toString(),
                            entry["note"].toString()
                        )
                    )
                }
                Log.d("Tasks", "$EntryContent.ENTRIES")
                Toast.makeText(this, "Fetched ${EntryContent.ENTRIES.size} entries", Toast.LENGTH_SHORT).show()
                findViewById<RecyclerView>(R.id.list).adapter?.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Request Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}