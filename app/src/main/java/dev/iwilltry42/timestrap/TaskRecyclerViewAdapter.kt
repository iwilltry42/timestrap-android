package dev.iwilltry42.timestrap

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import dev.iwilltry42.timestrap.tasks.TaskContent.Task

/**
 * [RecyclerView.Adapter] that can display a [Task].
 */
class TaskRecyclerViewAdapter(
    private val values: List<Task>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)
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
        fun bind(task: Task, clickListener: OnItemClickListener) {
            itemName.text = task.name

            itemView.setOnClickListener {
                clickListener.onItemClicked(task)
            }
        }
    }
}

// interface for custom on-click listeners for the cardview items
interface OnItemClickListener {
    fun onItemClicked(task: Task)
}