package dev.iwilltry42.timestrap

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainButton = findViewById<Button>(R.id.main_button)
        mainButton.setOnClickListener {
            requestTimestrap(this)
        }

    }

    private fun requestTimestrap(context: Context) {

        val address = findViewById<EditText>(R.id.address).text
        val username = findViewById<EditText>(R.id.username).text
        val password = findViewById<EditText>(R.id.password).text
        val auth = Base64.encodeToString("$username:$password".toByteArray(), Base64.NO_WRAP)

        val textField = findViewById<TextView>(R.id.main_text)
        textField.text = username.toString()

        val url = "$address/api/entries/"
        Log.i("Address", address.toString())
        val queue = Volley.newRequestQueue(context)

        val jsonObjectRequest = object: JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                println("Count: " + response.get("count"))
                textField.text = "Count of tasks: " + response.get("count").toString()
            },
            Response.ErrorListener { error ->
                Log.e("Request", error.toString())
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                var headers :HashMap<String, String> = HashMap<String, String> ()
                headers["authorization"] = "Basic $auth"
                return headers
            }
        }
        queue.add(jsonObjectRequest)

    }

}

