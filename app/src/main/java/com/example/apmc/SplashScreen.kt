package com.example.apmc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import android.widget.Toast

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        var sharedPreferences = getSharedPreferences("login", MODE_PRIVATE)
        var check = sharedPreferences.getBoolean("logged",false)



        Handler(Looper.getMainLooper()).postDelayed({

            if (check){

                startActivity(Intent(this, MainActivity::class.java))
                finish()

            }
            else{

                val loginA = Intent(this, login::class.java)
                startActivity(loginA)
                finish()
            }


        },3000)
    }
}