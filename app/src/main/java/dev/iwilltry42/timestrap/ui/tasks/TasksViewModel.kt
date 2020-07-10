package dev.iwilltry42.timestrap.ui.tasks
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dev.iwilltry42.timestrap.R

import dev.iwilltry42.timestrap.content.tasks.TaskContent.Task
import kotlinx.android.synthetic.main.fragment_task_list_item.view.*

/**
 * [RecyclerView.Adapter] that can display a [Task].
 */
class TaskRecyclerViewAdapter(
    private val values: List<Task>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder>() {

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
        private val itemDetail: TextView = view.findViewById(R.id.item_detail)

        override fun toString(): String {
            return super.toString() + " '" + itemName.text + "'"
        }

        // bind item to the view holder
        fun bind(task: Task, clickListener: OnItemClickListener) {
            itemName.text = task.name

            // Display the Task rate as a chip
            if (task.rate != null) {
                val chipGroup = this.itemView.findViewById<ChipGroup>(R.id.item_chip_group)
                chipGroup.removeAllViews() // remove all chips and then re-create them
                val chipTaskRate = Chip(this.itemView.context)
                chipTaskRate.text = this.itemView.context.getString(R.string.task_item_chip_rate, task.rate)
                chipGroup.addView(chipTaskRate)
                chipGroup.visibility = View.VISIBLE
            }


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