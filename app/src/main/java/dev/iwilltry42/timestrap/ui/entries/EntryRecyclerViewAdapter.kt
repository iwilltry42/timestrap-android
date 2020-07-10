package dev.iwilltry42.timestrap.ui.entries

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dev.iwilltry42.timestrap.R
import dev.iwilltry42.timestrap.content.entries.EntryContent
import java.text.SimpleDateFormat

/**
 * [RecyclerView.Adapter] that can display an [Entry].
 */
class EntryRecyclerViewAdapter(
    private val values: List<EntryContent.Entry>
) : RecyclerView.Adapter<EntryRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val itemName: TextView = view.findViewById(R.id.item_name)
        private val itemDetail: TextView = view.findViewById(R.id.item_detail)

        override fun toString(): String {
            return super.toString() + " '" + itemName.text + "'"
        }

        // bind item to the view holder
        fun bind(entry: EntryContent.Entry) {
            itemName.text = entry.id.toString()

            // display start and endtime if present
            if (entry.datetimeStart != null && entry.datetimeEnd != null) {
                val datefmt = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
                itemDetail.text = itemView.context.getString(R.string.entry_item_from_to, datefmt.format(entry.datetimeStart), datefmt.format(entry.datetimeEnd))
                itemDetail.visibility = View.VISIBLE
            }

        }
    }
}