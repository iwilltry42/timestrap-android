package dev.iwilltry42.timestrap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private var apiToken: String? = null
    private var address: String? = null
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppPreferences.init(this)
        setContentView(R.layout.activity_main)

        if (AppPreferences.isLoggedIn) {
            findViewById<EditText>(R.id.username).setText(AppPreferences.username)
            findViewById<EditText>(R.id.address).setText(AppPreferences.address)
        }

        // Sign In Button
        val mainButton = findViewById<Button>(R.id.signin_button)
        val progressBar = findViewById<ProgressBar>(R.id.signin_progress)
        mainButton.setOnClickListener {
            progressBar.visibility = ProgressBar.VISIBLE
            this.address = findViewById<EditText>(R.id.address)?.text.toString()
            this.username = findViewById<EditText>(R.id.username)?.text.toString()
            fetchAPITokenAndProceed(
                this.address!!,
                this.username!!,
                findViewById<EditText>(R.id.password)?.text.toString()
            )
        }

        // activate Sign In Button only when all fields are filled
        val fieldsFilledWatcher = object: TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mainButton.isEnabled = (findViewById<EditText>(R.id.address)?.text?.length!! > 0
                        && findViewById<EditText>(R.id.username)?.text?.length!! > 0
                        && findViewById<EditText>(R.id.password)?.text?.length!! > 0)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {}
        }

        findViewById<EditText>(R.id.address).addTextChangedListener(fieldsFilledWatcher)
        findViewById<EditText>(R.id.username).addTextChangedListener(fieldsFilledWatcher)
        findViewById<EditText>(R.id.password).addTextChangedListener(fieldsFilledWatcher)

    }

    private fun fetchAPITokenAndProceed(address: String, username: String, password: String) {
        val auth = Base64.encodeToString("$username:$password".toByteArray(), Base64.NO_WRAP)
        val bodyString = HashMap<String, String>()
        bodyString["username"] = username
        bodyString["password"] = password
        requestAPI(Request.Method.POST, address, "/api/auth_token/", null, JSONObject(bodyString as Map<*, *>).toString().toByteArray(), this::setAPITokenAndProceed)
    }

    private fun setAPITokenAndProceed(success: Boolean, response: JSONObject?) {
        if (success) {
            Log.e("API", "Fetched token ${response?.get("token")?.toString()}")
            apiToken = response?.get("token")?.toString()
            Toast.makeText(this, "Fetched API Token '${this.apiToken}'", Toast.LENGTH_SHORT).show()
            AppPreferences.token = apiToken!!
            AppPreferences.address = this.address!!
            AppPreferences.username = this.username!!
            AppPreferences.isLoggedIn = true
        } else {
            Toast.makeText(this, "Failed to fetch API Token: Please verify your Account Details", Toast.LENGTH_SHORT).show()
            Log.e("API", "Failed to fetch API Token")
            AppPreferences.isLoggedIn = false
        }
        findViewById<ProgressBar>(R.id.signin_progress).visibility = ProgressBar.INVISIBLE
        requestAPIWithTokenAuth(this.address!!, "/api/entries/", AppPreferences.token)
    }

    private fun requestAPIWithTokenAuth(address: String, path: String, token: String) {
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Token $token"
        requestAPI(Request.Method.GET, address, path, headers, null) { success, response ->
            if(success) {
                findViewById<TextView>(R.id.response_text).text =
                    getString(R.string.response_field, response?.get("count"))
            }
        }
    }

    private fun requestAPI(method: Int, address: String, path: String, headers: HashMap<String, String>?, body: ByteArray?, callback: (success: Boolean, response: JSONObject?) -> Unit) {



        val textField = findViewById<TextView>(R.id.main_text)

        val url = "$address$path"
        Log.i("Address", address)
        val queue = Volley.newRequestQueue(this)

        val jsonObjectRequest = object: JsonObjectRequest(
            method, url, null,
            Response.Listener { response ->
                callback(true, response)
            },
            Response.ErrorListener { error ->
                Log.e("Request", error.toString())
                error.printStackTrace()
                callback(false, null)
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                return headers ?: super.getHeaders()
            }

            override fun getBody(): ByteArray {
                return body ?: super.getBody()
            }
        }
        queue.add(jsonObjectRequest)
    }

}

