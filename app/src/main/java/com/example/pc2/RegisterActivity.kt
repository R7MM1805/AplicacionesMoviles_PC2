package com.example.pc2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request.Method
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_search -> {
                val intent = Intent(this, ListActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun registerStudent(view: View) {
        val txtCode = findViewById<EditText>(R.id.txtCode).text.toString()
        val txtName = findViewById<EditText>(R.id.txtName).text.toString()
        val txtLastName = findViewById<EditText>(R.id.txtLastName).text.toString()
        val txtSection = findViewById<EditText>(R.id.txtSection).text.toString()

        val url = "https://i9acjuyjt5.execute-api.us-east-1.amazonaws.com/v1/pc2"

        val jsonObject = JSONObject()
        try {
            jsonObject.put("codigo", txtCode)
            jsonObject.put("nombre", txtName)
            jsonObject.put("apellido", txtLastName)
            jsonObject.put("seccion", txtSection)
        } catch (e: JSONException){
            Log.i("Error: ", e.message ?: "Ocurrió un problema con el servicio de registro")
        }

        val jsonRequest = object : JsonObjectRequest(
            Method.POST, url, jsonObject, Response.Listener { reponse ->
                Log.i("Success: ", "Registro con exito")
            },
            Response.ErrorListener { error: VolleyError ->
                Log.i("Error: ", error.message ?: "Ocurrió un problema con el servicio de registro")
            }) {}

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonRequest)
    }
}