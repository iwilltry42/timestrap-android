package dev.iwilltry42.timestrap.ui.entries

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.LinearLayout
import android.widget.TextView
import dev.iwilltry42.timestrap.R
import dev.iwilltry42.timestrap.entity.Entry
import dev.iwilltry42.timestrap.fromHtml
import java.text.SimpleDateFormat

/**
 * [RecyclerView.Adapter] that can display an [Entry].
 */
class EntryRecyclerViewAdapter(
    private val values: List<Entry>
) : RecyclerView.Adapter<EntryRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item_list_item, parent, false)
        val stub = view.findViewById<ViewStub>(R.id.item_details_stub)
        stub.layoutResource = R.layout.item_details_entry
        stub.inflate()
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val itemName: TextView = view.findViewById(R.id.item_name)
        private val itemDetails: LinearLayout = view.findViewById(R.id.item_details)

        override fun toString(): String {
            return super.toString() + " '" + itemName.text + "'"
        }

        // bind item to the view holder
        fun bind(entry: Entry) {
            itemName.text = entry.id.toString()



            // display start and endtime if present
            if (
                entry.datetimeStart != null
                && entry.datetimeEnd != null
            ) {
                itemDetails.removeAllViews()
                val datefmt = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
                val txtStart = TextView(itemView.context)
                val txtEnd = TextView(itemView.context)
                itemDetails.addView(txtStart)
                itemDetails.addView(txtEnd)
                txtStart.text = fromHtml(itemView.context.getString(R.string.entry_item_datetime_start, datefmt.format(entry.datetimeStart)))
                txtEnd.text = fromHtml(itemView.context.getString(R.string.entry_item_datetime_end, datefmt.format(entry.datetimeEnd)))
                itemDetails.visibility = View.VISIBLE
            }

        }
    }
}