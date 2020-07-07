package dev.iwilltry42.timestrap.ui.projects
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dev.iwilltry42.timestrap.R

import dev.iwilltry42.timestrap.content.projects.ProjectContent.Project

/**
 * [RecyclerView.Adapter] that can display a [Project].
 */
class ProjectRecyclerViewAdapter(
    private val values: List<Project>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ProjectRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_task_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item, itemClickListener)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val itemName: TextView = view.findViewById(R.id.item_name)

        override fun toString(): String {
            return super.toString() + " '" + itemName.text + "'"
        }

        // bind item to the view holder
        fun bind(project: Project, clickListener: OnItemClickListener) {
            itemName.text = project.name

            itemView.setOnClickListener {
                clickListener.onItemClicked(project)
            }
        }
    }
}

// interface for custom on-click listeners for the cardview items
interface OnItemClickListener {
    fun onItemClicked(project: Project)
}