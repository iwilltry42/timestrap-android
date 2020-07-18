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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import dev.iwilltry42.timestrap.*
import dev.iwilltry42.timestrap.content.tasks.TASKS
import dev.iwilltry42.timestrap.content.tasks.Task
import dev.iwilltry42.timestrap.content.tasks.taskListTypeToken

/**
 * A fragment representing a list of Items.
 */
class TasksFragment : Fragment(), OnItemClickListener {

    private var columnCount = 2
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    // fetch and display the list of existing tasks from the Timestrap Server
    private fun fetchTasks() {
        requestAPIArrayWithTokenAuth(this.requireContext(), AppPreferences.address, "/api/tasks/", AppPreferences.token) { success, response ->
            if (success && response != null) {
                TASKS.clear()
                val gson = Gson()
                TASKS.addAll(gson.fromJson<MutableList<Task>>(response.toString(), taskListTypeToken))
                Log.d("Tasks", "${TASKS}")
                val toast = Toast.makeText(this.requireContext(), "Fetched ${TASKS.size} tasks", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.TOP, 0, 16)
                toast.show()
                view?.findViewById<RecyclerView>(R.id.list)?.adapter?.setTasks()
            } else {
                val toast = Toast.makeText(this.requireContext(), "Request Failed!", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.TOP, 0, 16)
                toast.show()
            }
        }
    }

    // custom on click listener implementing TaskRecyclerViewAdapter.OnItemClickListener
    override fun onItemClicked(task: Task) {
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
        val recyclerView = view.findViewById<RecyclerView>(R.id.list)

        // Set the adapter
        recyclerView.layoutManager = when {
            columnCount <= 1 -> LinearLayoutManager(context)
            else -> GridLayoutManager(context, columnCount)
        }

        val adapter = TaskRecyclerViewAdapter(TASKS, this)
        recyclerView.adapter = adapter

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        taskViewModel.allTasks.observe(this.activity, Observer { tasks ->
            tasks?.let {
                adapter.setTasks(tasks)
            }
        })

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