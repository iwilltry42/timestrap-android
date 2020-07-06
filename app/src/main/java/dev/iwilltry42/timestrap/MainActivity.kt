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
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppPreferences.init(this)
        setContentView(R.layout.activity_main)

        if (AppPreferences.isLoggedIn) {
            main_title.text = getString(R.string.main_text, AppPreferences.username)
        }

        main_button.setOnClickListener {
            getEntries()
        }
    }

    private fun getEntries() {
        requestAPIArrayWithTokenAuth(this, AppPreferences.address, "/api/tasks/", AppPreferences.token) {success, response ->
            val responseText = findViewById<TextView>(R.id.response_text)
            if (success) {
                responseText.text = getString(R.string.response_field, GsonBuilder().setPrettyPrinting().create().toJson(response))
            } else {
                responseText.text = "Request Failed!"
            }
        }
    }


}

