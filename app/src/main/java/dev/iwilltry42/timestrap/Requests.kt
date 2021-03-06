package dev.iwilltry42.timestrap

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

// send a generic request to the Timestrap expecting a JSONObject to be returned
fun requestAPIObject(context: Context, method: Int, address: String, path: String, headers: HashMap<String, String>?, body: ByteArray?, callback: (success: Boolean, response: JSONObject?) -> Unit) {

    val url = "$address$path"
    Log.d("RequestObject", url)
    val queue = Volley.newRequestQueue(context)

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

// send a generic request to the Timestrap expecting a JSONArray to be returned
fun requestAPIArray(context: Context, method: Int, address: String, path: String, headers: HashMap<String, String>?, body: ByteArray?, callback: (success: Boolean, response: JSONArray?) -> Unit) {

    val url = "$address$path"
    Log.d("RequestArray", url)
    val queue = Volley.newRequestQueue(context)

    val jsonArrayRequest = object: JsonArrayRequest(
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
    queue.add(jsonArrayRequest)
}

// send a token-authenticated request to the Timestrap expecting a JSONObject to be returned
fun requestAPIObjectWithTokenAuth(context: Context, address: String, path: String, token: String, callback: (success: Boolean, response: JSONObject?) -> Unit) {
    val headers = HashMap<String, String>()
    headers["Authorization"] = "Token $token"
    requestAPIObject(context, Request.Method.GET, address, path, headers, null, callback)
}

// send a token-authenticated request to the Timestrap expecting a JSONArray to be returned
fun requestAPIArrayWithTokenAuth(context: Context, address: String, path: String, token: String, callback: (success: Boolean, response: JSONArray?) -> Unit) {
    val headers = HashMap<String, String>()
    headers["Authorization"] = "Token $token"
    requestAPIArray(context, Request.Method.GET, address, path, headers, null, callback)
}