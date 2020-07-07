package dev.iwilltry42.timestrap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.widget.*
import com.android.volley.Request
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject


class LoginActivity : AppCompatActivity() {

    private var apiToken: String? = null
    private var address: String? = null
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppPreferences.init(this)
        setContentView(R.layout.activity_login)

        // If the user logged in before, auto-fill username and address from AppPreferences
        if (AppPreferences.isLoggedIn) {
            findViewById<EditText>(R.id.username).setText(AppPreferences.username)
            findViewById<EditText>(R.id.address).setText(AppPreferences.address)
            requestAPIArrayWithTokenAuth(this, AppPreferences.address, "/api/tasks", AppPreferences.token) { success, _ ->
                if (success) {
                    login_title.text = "Logged in as ${AppPreferences.username}"
                } else {
                    login_title.text = "Failed Login, Try Again!"
                }
            }
        }

        // Sign In Button
        val loginButton = findViewById<Button>(R.id.login_button)
        val progressBar = findViewById<ProgressBar>(R.id.signin_progress)
        loginButton.setOnClickListener {
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
                loginButton.isEnabled = (findViewById<EditText>(R.id.address)?.text?.length!! > 0
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

    // Use basic auth against the API to retrieve an API-Token -> on callback, proceed with setting the user details
    private fun fetchAPITokenAndProceed(address: String, username: String, password: String) {
        val auth = Base64.encodeToString("$username:$password".toByteArray(), Base64.NO_WRAP)
        val bodyString = HashMap<String, String>()
        bodyString["username"] = username
        bodyString["password"] = password
        requestAPIObject(this, Request.Method.POST, address, "/api/auth_token/", null, JSONObject(bodyString as Map<*, *>).toString().toByteArray(), this::setAPITokenAndProceed)
    }

    // If an API-Token could be fetched, save the user's details in App Preferences and move on to the next activity
    private fun setAPITokenAndProceed(success: Boolean, response: JSONObject?) {
        if (success) {
            Log.e("API", "Fetched token ${response?.get("token")?.toString()}")
            apiToken = response?.get("token")?.toString()
            Toast.makeText(this, "Fetched API Token '${this.apiToken}'", Toast.LENGTH_SHORT).show()

            // Login succeeded: store details in AppPreferences
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
        val intent = Intent(this, MainActivity::class.java).apply {}
        startActivity(intent)
    }
}

