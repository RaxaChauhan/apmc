@file:Suppress("DEPRECATION")

package com.example.apmc

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class login : AppCompatActivity() {

    lateinit var uname: EditText
    lateinit var uno: EditText
    lateinit var ucity: EditText
    lateinit var uLogin:Button
    lateinit var sp : SharedPreferences
    private lateinit var networkConnection: NetworkConnection
    private lateinit var jsonResponse : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        uname = findViewById(R.id.uname)
        uno = findViewById(R.id.uno)
        ucity = findViewById(R.id.ucity)
        uLogin = findViewById(R.id.submit)


        sp = getSharedPreferences("login",MODE_PRIVATE)




        //internet check
        networkConnection = NetworkConnection(applicationContext)
        val inflateLayout = findViewById<View>(R.id.network_error)

        networkConnection.observe(this){
            if(it){

              //  var userName = sp.getString("uname","")
                //Toast.makeText(this,userName,Toast.LENGTH_SHORT).show()
                inflateLayout.visibility = View.GONE

                uLogin.setOnClickListener(object : View.OnClickListener {
                    @SuppressLint("HardwareIds")
                    override fun onClick(p0: View?) {

                        var uName = uname.getText().toString().trim()
                        var uNo = uno.getText().toString().trim()
                        var uCity = ucity.getText().toString().trim()
                        val deviceId: String = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

                        if(uName.isNotEmpty() && uNo.isNotEmpty() && uCity.isNotEmpty()){

                            if(uNo.length == 10){

                                if(uNo.startsWith("7") || uNo.startsWith("8") || uNo.startsWith("9")){

                                    //store data in sp
                                    sp.edit().putBoolean("logged",true).apply()
                                    sp.edit().putString("uname",uName).commit()
                                    sp.edit().putString("mobileNo",uNo).commit()

                                    //insert in api
                                    postData().execute(uName,uNo,uCity,deviceId)

                                    goToMainActivity()

                                }
                                else{

                                    Toast.makeText(this@login, "The mobile number is not Indian.",Toast.LENGTH_SHORT).show()

                                }


                            }
                            else{
                                Toast.makeText(this@login, "Mobile number should have 10 digits",Toast.LENGTH_SHORT).show()
                            }

                        }
                        else{

                           Toast.makeText(this@login, "enter values",Toast.LENGTH_SHORT).show()

                        }

                    }
                })
            }
            else{

                uLogin.isEnabled = false
                uLogin.isClickable = false
                inflateLayout.visibility = View.VISIBLE

            }
        }

    }




    fun goToMainActivity() {
        val main: Intent = Intent(this@login, MainActivity::class.java)
        startActivity(main)
        finish()
    }

    inner class postData : AsyncTask<String, String, String>(){

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: String?): String {

            val method = "insert_farmer_data"

            val response = CallWebService().callApi(method, "name" to params[0], "mobile_no" to params[1], "village" to params[2], "device_id" to params[3])

            Log.v("response", "response==" + response)
            return response
        }



    }
}