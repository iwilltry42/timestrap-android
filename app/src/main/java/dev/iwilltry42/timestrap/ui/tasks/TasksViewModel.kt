package dev.iwilltry42.timestrap.ui.tasks
import android.app.Application
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dev.iwilltry42.timestrap.R
import dev.iwilltry42.timestrap.content.tasks.Task
import dev.iwilltry42.timestrap.db.TimestrapRepository
import dev.iwilltry42.timestrap.db.TimestrapRoomDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * [RecyclerView.Adapter] that can display a [Task].
 */
class TaskRecyclerViewAdapter(
    private var tasks: List<Task>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item_list_item, parent, false)
        val stub = view.findViewById<ViewStub>(R.id.item_details_stub)
        stub.layoutResource = R.layout.item_details_entry
        stub.inflate()
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = tasks[position]
        holder.bind(item, itemClickListener)
    }

    override fun getItemCount(): Int = tasks.size

    fun setTasks(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val itemName: TextView = view.findViewById(R.id.item_name)
        private val itemDetails: LinearLayout = view.findViewById(R.id.item_details)

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

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TimestrapRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allTasks: LiveData<List<Task>>

    init {
        val tasksDao = TimestrapRoomDB.getDatabase(application, viewModelScope).taskDao()
        repository = TimestrapRepository(tasksDao)
        allTasks = repository.allTasks
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(task)
    }

}