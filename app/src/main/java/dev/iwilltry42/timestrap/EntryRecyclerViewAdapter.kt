package dev.iwilltry42.timestrap

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dev.iwilltry42.timestrap.content.entries.EntryContent

/**
 * [RecyclerView.Adapter] that can display an [Entry].
 */
class EntryRecyclerViewAdapter(
    private val values: List<EntryContent.Entry>
) : RecyclerView.Adapter<EntryRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_detail_entry_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val itemName: TextView = view.findViewById(R.id.item_entry_name)
        private val itemDate: TextView = view.findViewById(R.id.item_entry_date)

        override fun toString(): String {
            return super.toString() + " '" + itemName.text + "'"
        }

        // bind item to the view holder
        fun bind(entry: EntryContent.Entry) {
            itemName.text = entry.id.toString()
            itemDate.text = entry.datetimeStart.toString() + " -> " + entry.datetimeEnd.toString()

        }
    }
}