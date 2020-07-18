package dev.iwilltry42.timestrap.ui.clients

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
import dev.iwilltry42.timestrap.*
import dev.iwilltry42.timestrap.content.clients.CLIENTS
import dev.iwilltry42.timestrap.content.clients.Client
import dev.iwilltry42.timestrap.content.clients.clientListTypeToken

/**
 * A fragment representing a list of Items.
 */
class ClientsFragment : Fragment(), OnItemClickListener {

    private var columnCount = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

        fetchClients()
    }

    // fetch and display the list of existing tasks from the Timestrap Server
    private fun fetchClients() {
        requestAPIArrayWithTokenAuth(
            this.requireContext(),
            AppPreferences.address,
            "/api/clients/",
            AppPreferences.token
        ) { success, response ->
            if (success && response != null) {
                CLIENTS.clear()
                val gson = Gson()
                CLIENTS.addAll(gson.fromJson<MutableList<Client>>(response.toString(), clientListTypeToken))
                Log.d("Clients", "$CLIENTS")
                val toast = Toast.makeText(
                    this.requireContext(),
                    "Fetched ${CLIENTS.size} clients",
                    Toast.LENGTH_SHORT
                )
                toast.setGravity(Gravity.TOP, 0, 16)
                toast.show()
                view?.findViewById<RecyclerView>(R.id.list)?.adapter?.notifyDataSetChanged()
            } else {
                val toast = Toast.makeText(this.requireContext(), "Request Failed!", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.TOP, 0, 16)
                toast.show()
            }
        }
    }

    // custom on click listener implementing TaskRecyclerViewAdapter.OnItemClickListener
    override fun onItemClicked(client: Client) {
        Log.i("Clicked Task", client.name)
        val toast = Toast.makeText(this.context, "Checkout ${client.url}", Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP, 0, 16)
        toast.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            val useAdapter = ClientRecyclerViewAdapter(CLIENTS, this)
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
            ClientsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
