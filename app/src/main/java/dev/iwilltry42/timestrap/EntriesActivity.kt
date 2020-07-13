package dev.iwilltry42.timestrap

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import dev.iwilltry42.timestrap.content.entries.EntryContent
import dev.iwilltry42.timestrap.content.entries.formatDate
import org.json.JSONArray
import java.text.SimpleDateFormat

const val EXTRA_TASK_ID = "dev.iwilltry42.timestrap.TASK_ID"
const val EXTRA_PROJECT_ID = "dev.iwilltry42.timestrap.PROJECT_ID"

class EntriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entries)

        findViewById<TextView>(R.id.text_task_title).text = "Entries"

        fetchEntries("" +
            "task=${if(intent.getStringExtra(EXTRA_TASK_ID) != null) intent.getStringExtra(EXTRA_TASK_ID) else ""}" +
            "&project=${if(intent.getStringExtra(EXTRA_PROJECT_ID) != null) intent.getStringExtra(EXTRA_PROJECT_ID) else ""}" +
        "")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
                true
            }
        }
    }

    // fetch and display the list of existing entries from the Timestrap Server
    private fun fetchEntries(filter: String?) {
        requestAPIObjectWithTokenAuth(this, AppPreferences.address, "/api/entries/?${filter}", AppPreferences.token) {success, response ->
            if (success && response != null) {
                val resultList: JSONArray = response.getJSONArray("results")
                EntryContent.ENTRIES.clear()
                for (i in 0 until  resultList.length()) {
                    val entry = resultList.getJSONObject(i)
                    EntryContent.ENTRIES.add(
                        i,
                        EntryContent.Entry(
                            entry["id"].toString().toInt(),
                            entry["url"].toString(),
                            entry["project"].toString(),
                            entry["task"].toString(),
                            entry["user"].toString(),
                            SimpleDateFormat("yyyy-MM-dd").parse(entry["date"].toString()),
                            entry["duration"].toString(),
                            formatDate(entry["datetime_start"].toString()),
                            formatDate(entry["datetime_end"].toString()),
                            entry["note"].toString()
                        )
                    )
                }
                Log.d("Entries", "${EntryContent.ENTRIES}")
                EntryContent.ENTRIES.sortBy {it.id}
                findViewById<RecyclerView>(R.id.list).adapter?.notifyDataSetChanged()
                val toast = Toast.makeText(this, "Fetched ${response["count"]} entries", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.TOP, 0, 16)
                toast.show()
            } else {
                val toast = Toast.makeText(this, "Request Failed!", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.TOP, 0, 16)
                toast.show()
            }
        }
    }
}