package dev.iwilltry42.timestrap.ui.settings

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dev.iwilltry42.timestrap.R

import dev.iwilltry42.timestrap.ui.settings.Settings.Setting

/**
 * [RecyclerView.Adapter] that can display a [Setting].
 */
class SettingsRecyclerViewAdapter(
    private val values: List<Setting>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<SettingsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_settings_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item, itemClickListener)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val itemName: TextView = view.findViewById(R.id.settings_list_item_title)

        override fun toString(): String {
            return super.toString() + " '" + itemName.text + "'"
        }

        // bind item to the view holder
        fun bind(setting: Setting, clickListener: OnItemClickListener) {
            itemName.text = setting.name

            itemView.setOnClickListener {
                clickListener.onItemClicked(setting)
            }
        }
    }
}

// interface for custom on-click listeners for the cardview items
interface OnItemClickListener {
    fun onItemClicked(setting: Setting)
}