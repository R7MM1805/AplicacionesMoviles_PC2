package com.example.pc2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        searhStudents()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_register -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun searhStudents(){
        val url = "https://i9acjuyjt5.execute-api.us-east-1.amazonaws.com/v1/pc2"
        val stringRequest = JsonObjectRequest(
            Request.Method.GET, url, null, {
                response -> try {
                    val jsonArray = response.getJSONArray("data")
                    val items = mutableListOf<String>()
                    for (i in 0 until jsonArray.length()){
                        val student = jsonArray.getJSONObject(i)
                        val code = student.getString("codigo")
                        val name = student.getString("nombre")
                        val lastName = student.getString("apellido")
                        val section = student.getString("seccion")
                        items.add("$code - $name - $lastName - $section")
                    }
                    val lvStudents: ListView = findViewById(R.id.lvStudents)
                    val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
                    lvStudents.adapter = adapter

                } catch (e: JSONException){
                    Log.i("Error:", e.message.toString())
                }
            }, {
                error -> Log.i("Error: ", error.toString())
            }
        )
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }
}