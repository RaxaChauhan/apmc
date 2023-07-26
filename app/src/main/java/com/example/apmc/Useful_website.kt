package com.example.apmc

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

@Suppress("DEPRECATION")
class Useful_website : AppCompatActivity() {

    private lateinit var btnRecyclerView : RecyclerView
    private lateinit var jsonResponse : String
    private lateinit var btnAdapter: webBtnAdapter

    private lateinit var networkConnection: NetworkConnection

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_useful_website)

        supportActionBar?.setTitle("ઉપયોગી વેબસાઇટ")
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getWebLink().execute()

        //internet
        val inflateLayout = findViewById<View>(R.id.network_error)
        networkConnection = NetworkConnection(applicationContext)

        networkConnection.observe(this){
            if(it){
                inflateLayout.visibility = View.GONE
            }
            else{
                inflateLayout.visibility = View.VISIBLE

            }
        }

    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    inner class getWebLink : AsyncTask<String, String, String>(){

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: String?): String {

            val method = "display_usefullinks"

            val response = CallWebService().callApi(method)
            Log.v("response", "response==" + response)

            return response
        }

        @SuppressLint("NotifyDataSetChanged")
        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Log.v("responce", "responce==" + result)
            try{
                jsonResponse = result.toString()
                val gson = Gson()
                val response = gson.fromJson(jsonResponse, Response::class.java)
                var jresult = response.result

                if (jresult != null && jresult.isNotEmpty()){

                    //recycleview
                    btnRecyclerView = findViewById(R.id.btnrecyclerView)
                    btnAdapter = webBtnAdapter(jresult)

                    btnRecyclerView.layoutManager = LinearLayoutManager(this@Useful_website)
                    btnRecyclerView.adapter = btnAdapter

                }
                else{
                    Toast.makeText(this@Useful_website, "No Data", Toast.LENGTH_SHORT ).show()
                }

            }
            catch (e:Exception){
                e.printStackTrace()
            }

        }

    }
}