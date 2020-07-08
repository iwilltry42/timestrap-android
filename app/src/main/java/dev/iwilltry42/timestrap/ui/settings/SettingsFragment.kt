package dev.iwilltry42.timestrap.ui.settings

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import dev.iwilltry42.timestrap.*
import dev.iwilltry42.timestrap.content.settings.SettingsContent

/**
 * A fragment representing a list of Items.
 */
class SettingsFragment : Fragment(), OnItemClickListener {

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

        SettingsContent.SETTINGS.clear()

        SettingsContent.SETTINGS.add(
            0, SettingsContent.Setting(
                "Test-Setting",
                MainActivity::class.java
            )
        )

        SettingsContent.SETTINGS.add(
            1, SettingsContent.Setting(
                "Other Setting",
                MainActivity::class.java
            )
        )

    }


    // custom on click listener implementing TaskRecyclerViewAdapter.OnItemClickListener
    override fun onItemClicked(setting: SettingsContent.Setting) {
        Log.i("Clicked Setting", setting.name)
        Toast.makeText(this.context, "Clicked Setting ${setting.name}", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            val useAdapter = SettingsRecyclerViewAdapter(SettingsContent.SETTINGS, this)
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
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
