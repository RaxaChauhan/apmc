package com.example.apmc

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

@Suppress("DEPRECATION")
class Review : AppCompatActivity() {

    private lateinit var networkConnection: NetworkConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        supportActionBar?.setTitle("રીવ્યુ")
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val rTxt = findViewById<TextView>(R.id.reviewTxt)
        val rbtn = findViewById<Button>(R.id.reviewBtn)

        var sp = getSharedPreferences("login",MODE_PRIVATE)




        //internet
        val inflateLayout = findViewById<View>(R.id.network_error)
        networkConnection = NetworkConnection(applicationContext)

        networkConnection.observe(this){
            if(it){
                inflateLayout.visibility = View.GONE

                rbtn.setOnClickListener(object : View.OnClickListener {
                    @SuppressLint("HardwareIds")
                    override fun onClick(p0: View?) {

                        val ReviewTxt = rTxt.text.toString().trim()

                        if(ReviewTxt.isNotEmpty()){

                            var mobileNo = sp.getString("mobileNo", "")
                            postReviw().execute(mobileNo,ReviewTxt)
                            var intent = getIntent()
                            finish()
                            startActivity(intent)
                            Toast.makeText(this@Review, "Review Submitted", Toast.LENGTH_SHORT).show()
                        }
                        else{

                            Toast.makeText(this@Review, "Write Review", Toast.LENGTH_SHORT).show()

                        }

                    }
                })
            }
            else{
                inflateLayout.visibility = View.VISIBLE
                rbtn.isEnabled = false
                rbtn.isClickable = false
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
    inner class postReviw : AsyncTask<String, String, String>() {

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: String?): String {

            val method = "insert_review"

            val response = CallWebService().callApi(
                method,
                "user_id" to params[0],
                "review" to params[1]
            )

            Log.v("response", "response==" + response)


            return response
        }
    }
}