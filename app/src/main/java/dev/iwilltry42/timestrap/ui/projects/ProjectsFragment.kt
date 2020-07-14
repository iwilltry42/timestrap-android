package dev.iwilltry42.timestrap.ui.projects

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
import com.google.gson.reflect.TypeToken
import dev.iwilltry42.timestrap.*
import dev.iwilltry42.timestrap.content.projects.ProjectContent

/**
 * A fragment representing a list of Items.
 */
class ProjectsFragment : Fragment(), OnItemClickListener {

    private var columnCount = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

        fetchProjects()
    }

    // fetch and display the list of existing tasks from the Timestrap Server
    private fun fetchProjects() {
        requestAPIArrayWithTokenAuth(
            this.requireContext(),
            AppPreferences.address,
            "/api/projects/",
            AppPreferences.token
        ) { success, response ->
            if (success && response != null) {
                ProjectContent.PROJECTS.clear()
                val gson = Gson()
                ProjectContent.PROJECTS.addAll(gson.fromJson<MutableList<ProjectContent.Project>>(response.toString(), ProjectContent.listTypeToken))
                val toast = Toast.makeText(
                    this.requireContext(),
                    "Fetched ${ProjectContent.PROJECTS.size} projects",
                    Toast.LENGTH_SHORT
                )
                toast.setGravity(Gravity.TOP, 0, 16)
                toast.show()
                view?.findViewById<RecyclerView>(R.id.list)?.adapter?.notifyDataSetChanged()
            } else {
                val toast= Toast.makeText(this.requireContext(), "Request Failed!", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.TOP, 0, 16)
                toast.show()
            }
        }
    }

    // custom on click listener implementing TaskRecyclerViewAdapter.OnItemClickListener
    override fun onItemClicked(project: ProjectContent.Project) {
        val intent = Intent(activity, EntriesActivity::class.java).apply {
            putExtra(EXTRA_PROJECT_ID, project.id.toString())
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
            val useAdapter = ProjectRecyclerViewAdapter(ProjectContent.PROJECTS, this)
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
            ProjectsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
