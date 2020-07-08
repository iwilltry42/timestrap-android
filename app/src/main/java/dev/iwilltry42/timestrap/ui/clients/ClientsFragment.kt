package dev.iwilltry42.timestrap.ui.clients

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
import dev.iwilltry42.timestrap.content.clients.ClientContent

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
                ClientContent.CLIENTS.clear()
                for (i in 0 until response.length()) {
                    val client = response.getJSONObject(i)
                    ClientContent.CLIENTS.add(
                        i,
                        ClientContent.Client(
                            client["id"].toString().toInt(),
                            client["url"].toString(),
                            client["name"].toString(),
                            client["payment_id"].toString(),
                            client["archive"].toString().toBoolean(),
                            client["total_projects"].toString().toInt(),
                            client["total_duration"].toString()
                        )
                    )
                }
                Log.d("Clients", "$ClientContent.CLIENTS")
                Toast.makeText(
                    this.requireContext(),
                    "Fetched ${ClientContent.CLIENTS.size} clients",
                    Toast.LENGTH_SHORT
                ).show()
                view?.findViewById<RecyclerView>(R.id.list)?.adapter?.notifyDataSetChanged()
            } else {
                Toast.makeText(this.requireContext(), "Request Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // custom on click listener implementing TaskRecyclerViewAdapter.OnItemClickListener
    override fun onItemClicked(client: ClientContent.Client) {
        Log.i("Clicked Task", client.name)
        Toast.makeText(this.context, "Checkout ${client.url}", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            val useAdapter = ClientRecyclerViewAdapter(ClientContent.CLIENTS, this)
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