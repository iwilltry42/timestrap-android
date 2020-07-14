package dev.iwilltry42.timestrap.ui.tasks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import dev.iwilltry42.timestrap.*
import dev.iwilltry42.timestrap.content.entries.EntryContent
import dev.iwilltry42.timestrap.content.tasks.TaskContent
import org.json.JSONObject

/**
 * A fragment representing a list of Items.
 */
class TasksFragment : Fragment(), OnItemClickListener {

    private var columnCount = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

        fetchTasks()
    }

    // fetch and display the list of existing tasks from the Timestrap Server
    private fun fetchTasks() {
        requestAPIArrayWithTokenAuth(this.requireContext(), AppPreferences.address, "/api/tasks/", AppPreferences.token) { success, response ->
            if (success && response != null) {
                TaskContent.TASKS.clear()
                val gson = Gson()
                TaskContent.TASKS.addAll(gson.fromJson<MutableList<TaskContent.Task>>(response.toString(),TaskContent.listTypeToken))
                Log.d("Tasks", "${TaskContent.TASKS}")
                val toast = Toast.makeText(this.requireContext(), "Fetched ${TaskContent.TASKS.size} tasks", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.TOP, 0, 16)
                toast.show()
                view?.findViewById<RecyclerView>(R.id.list)?.adapter?.notifyDataSetChanged()
            } else {
                val toast = Toast.makeText(this.requireContext(), "Request Failed!", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.TOP, 0, 16)
                toast.show()
            }
        }
    }

    // custom on click listener implementing TaskRecyclerViewAdapter.OnItemClickListener
    override fun onItemClicked(task: TaskContent.Task) {
        Log.i("Clicked Task", task.name)
        val intent = Intent(super.getContext(), EntriesActivity::class.java).apply {
            putExtra(EXTRA_TASK_ID, task.id.toString())
        }
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            val useAdapter = TaskRecyclerViewAdapter(TaskContent.TASKS, this)
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = useAdapter
            }
        }
        return view
    }

    companion object {

        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            TasksFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}