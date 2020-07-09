package dev.iwilltry42.timestrap.ui.tasks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import dev.iwilltry42.timestrap.*
import dev.iwilltry42.timestrap.content.tasks.TaskContent

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
                for (i in 0 until  response.length()) {
                    val task = response.getJSONObject(i)
                    TaskContent.TASKS.add(i,
                        TaskContent.Task(
                            task["id"].toString().toInt(),
                            task["name"].toString(),
                            task["hourly_rate"].toString(),
                            task["url"].toString()))
                }
                Log.d("Tasks", "$TaskContent.ITEMS")
                Toast.makeText(this.requireContext(), "Fetched ${TaskContent.TASKS.size} tasks", Toast.LENGTH_SHORT).show()
                view?.findViewById<RecyclerView>(R.id.list)?.adapter?.notifyDataSetChanged()
            } else {
                Toast.makeText(this.requireContext(), "Request Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // custom on click listener implementing TaskRecyclerViewAdapter.OnItemClickListener
    override fun onItemClicked(task: TaskContent.Task) {
        Log.i("Clicked Task", task.name)
        val intent = Intent(super.getContext(), EntriesActivity::class.java).apply {
            putExtra(EXTRA_TASK_NAME, task.name)
            putExtra(EXTRA_TASK_ID, task.id.toString())
        }
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)

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