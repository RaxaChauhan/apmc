package com.example.apmc

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Suppress("DEPRECATION")
class frtDaily_price : AppCompatActivity() {

    private lateinit var tableRecyclerView : RecyclerView
    private lateinit var jsonResponse : String
    private lateinit var tableRowAdapter: TableRowAdapter
    lateinit var date:String

    var textview_date: TextView? = null
    var cal = Calendar.getInstance()




    private lateinit var networkConnection: NetworkConnection


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frt_daily_price)

        supportActionBar?.setTitle("દૈનીક ભાવ")
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //date
        textview_date = findViewById(R.id.frtdate)
        val Currentdate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        getjanshiPrice().execute(Currentdate)
        textview_date!!.text = Currentdate
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "yyyy/MM/dd" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                date = sdf.format(cal.getTime())

                textview_date!!.text = date
                getjanshiPrice().execute(date)
            }
        }

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        textview_date!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@frtDaily_price,
                    R.style.DatePickerDialogTheme,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })


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
        startActivity(Intent(this@frtDaily_price, MainActivity::class.java))
        finish()
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    inner class getjanshiPrice : AsyncTask<String, String, String>(){

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: String?): String {

            val method = "display_frt_daily_price"

            val response = CallWebService().callApi(method,
                ("date" to params[0])
            )
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
                    tableRecyclerView = findViewById(R.id.frt_recycler_view)
                    tableRowAdapter = TableRowAdapter(jresult)

                    tableRecyclerView.layoutManager = LinearLayoutManager(this@frtDaily_price)
                    tableRecyclerView.adapter = tableRowAdapter

                }
                else{
                    Toast.makeText(this@frtDaily_price, "no data", Toast.LENGTH_SHORT ).show()
                    tableRowAdapter?.clearData() // Assuming your adapter has a method to clear the data
                    tableRowAdapter?.notifyDataSetChanged()
                }

            }
            catch (e:Exception){
                e.printStackTrace()
            }

        }

    }
}