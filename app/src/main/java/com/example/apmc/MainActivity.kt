package com.example.apmc

import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.text.HtmlCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apmc.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.smarteist.autoimageslider.SliderView

@Suppress("DEPRECATION")

class MainActivity : AppCompatActivity() {


    lateinit var toggle: ActionBarDrawerToggle
    lateinit var binding: ActivityMainBinding
    lateinit var imageUrl: ArrayList<Int>
    lateinit var sliderView: SliderView
    lateinit var sliderAdapter: SliderAdapter
    private lateinit var networkConnection: NetworkConnection
    private lateinit var jsonResponse : String
    var marqueeText  = StringBuilder()
    lateinit var drawerlayout : DrawerLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val janshiP:Intent = Intent(this@MainActivity, regularPrice::class.java)
        val review:Intent = Intent(this@MainActivity, Review::class.java)
        val usefulWeb:Intent = Intent(this@MainActivity, Useful_website::class.java)
       drawerlayout= findViewById(R.id.drawerlayout)

        getallPrice().execute()

        val toolbar = findViewById<Toolbar>(R.id.head)
        setSupportActionBar(toolbar)

        binding.apply{
            toggle = ActionBarDrawerToggle(this@MainActivity,drawerlayout,toolbar,R.string.open,R.string.close)

            drawerlayout.addDrawerListener(toggle)
            toggle.syncState()



            supportActionBar?.title = null
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.menu)


            navView.itemIconTintList = null
            var headerView = navView.getHeaderView(0)
            var uName = headerView.findViewById<TextView>(R.id.userName)

            //shered preferences

            var sharedPreferences = getSharedPreferences("login", MODE_PRIVATE)
            var uNameValue = sharedPreferences.getString("uname","")
            uName.text = uNameValue


            navView.setNavigationItemSelectedListener {
               when(it.itemId){
                   R.id.menu1 -> {
                       val home:Intent = intent
                       startActivity(home)
                       finish()
                   }
                   R.id.menu2 -> {
                       startActivity(janshiP)
                       finish()
                   }
                   R.id.menu3 -> {
                       startActivity(Intent(this@MainActivity, vegDaily_price::class.java))
                       finish()
                   }
                   R.id.menu4 -> {
                       startActivity(Intent(this@MainActivity, frtDaily_price::class.java))
                       finish()
                   }
                   R.id.menu5 -> {
                       startActivity(Intent(this@MainActivity, janshiDailyIncome::class.java))
                       finish()
                   }
                   R.id.menu6 -> {
                       startActivity(Intent(this@MainActivity, vegDailyIncome::class.java))
                       finish()
                   }
                   R.id.menu7 -> {
                       startActivity(Intent(this@MainActivity, frtDailyIncome::class.java))
                       finish()
                   }
                   R.id.menu8 -> {
                       startActivity(review)
                       finish()
                   }
                   R.id.menu9 -> {
                       startActivity(usefulWeb)
                       finish()
                   }
                }
                true
            }
        }

        //for marquee



        val marquee: TextView = findViewById(R.id.marq)

        //marqueeText = "કપાસ"+"<font color=${Color.GREEN}> ↑ 0 </font>"+"<font color=${Color.RED}>↓ 0 </font>" + "કપાસ"+"<font color=${Color.GREEN}> ↑ 0 </font>"+"<font color=${Color.RED}>↓ 0 </font>" + "કપાસ"+"<font color=${Color.GREEN}> ↑ 0 </font>"+"<font color=${Color.RED}>↓ 0 </font>" + "કપાસ"+"<font color=${Color.GREEN}> ↑ 0 </font>"+"<font color=${Color.RED}>↓ 0 </font>"
       // marquee.text = Html.fromHtml(marqueeText.toString())

        marquee.isSelected = true

        //slidder
        sliderView = findViewById(R.id.slider)
        imageUrl = ArrayList()

        imageUrl.add(R.drawable.s1)
        imageUrl.add(R.drawable.s3)
        imageUrl.add(R.drawable.s4)

        sliderAdapter = SliderAdapter( imageUrl)
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()

        //network check
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

        //redirect
        val janshiPrice = findViewById<ImageView>(R.id.janshiPriceImg)
        janshiPrice.setOnClickListener{
            startActivity(janshiP)
            finish()
        }

        val VegPriceImg = findViewById<ImageView>(R.id.VegPriceImg)
        VegPriceImg.setOnClickListener{
            startActivity(Intent(this@MainActivity, vegDaily_price::class.java))
            finish()
        }

        val FruitPriceImg = findViewById<ImageView>(R.id.FruitPriceImg)
        FruitPriceImg.setOnClickListener{
            startActivity(Intent(this@MainActivity, frtDaily_price::class.java))
            finish()
        }

        val janshiInImg = findViewById<ImageView>(R.id.janshiInImg)
        janshiInImg.setOnClickListener{
            startActivity(Intent(this@MainActivity, janshiDailyIncome::class.java))
            finish()
        }

        val vegitablesInImg = findViewById<ImageView>(R.id.vegitablesInImg)
        vegitablesInImg.setOnClickListener{
            startActivity(Intent(this@MainActivity, vegDailyIncome::class.java))
            finish()
        }

        val FruitInImg = findViewById<ImageView>(R.id.FruitInImg)
        FruitInImg.setOnClickListener{
            startActivity(Intent(this@MainActivity, frtDailyIncome::class.java))
            finish()
        }

        val reviewImg = findViewById<ImageView>(R.id.rivyuImg)
        reviewImg.setOnClickListener{
            startActivity(review)
            finish()
        }

        val webImg = findViewById<ImageView>(R.id.webImg)
        webImg.setOnClickListener{
            startActivity(usefulWeb)
            finish()
        }



    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {

        if (drawerlayout.isDrawerOpen(GravityCompat.START)) {
            drawerlayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDestroy() {
        super.onDestroy()
        networkConnection.onInactive()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }


    inner class getallPrice : AsyncTask<String, String, String>(){

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: String?): String {

            val method = "display_all"

            val response = CallWebService().callApi(method)
            Log.v("response", "response==" + response)
            return response
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Log.v("responce", "responce==" + result)
            try{
                jsonResponse = result.toString()
                val gson = Gson()
                val response = gson.fromJson(jsonResponse, Response::class.java)
                Log.v("responce", "name=====" + response.result[1].name)
                //val item: Item = gson.fromJson(jsonResponse, Item::class.java)
                //val jresult = response

                for(item in response.result){

                    val itemName = item.name
                    val minPrice = item.min_price
                    val maxPrice = item.max_price

                    marqueeText.append(" ")
                    marqueeText.append(itemName)
                    marqueeText.append(" ")
                    marqueeText.append("<font color=\"${Color.GREEN}\">↑ $minPrice</font>")
                    marqueeText.append(" ")
                    marqueeText.append("<font color=\"${Color.RED}\">↓ $maxPrice</font>")

                }

                val marquee: TextView = findViewById(R.id.marq)
                marquee.text = HtmlCompat.fromHtml(marqueeText.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)

            }
            catch (e:Exception){
                e.printStackTrace()
            }

        }

    }
}