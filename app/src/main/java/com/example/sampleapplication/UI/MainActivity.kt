package com.example.sampleapplication.UI

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapplication.Model.PrefsModel
import com.example.sampleapplication.Model.WeatherModel
import com.example.sampleapplication.R
import com.example.sampleapplication.Utils.ConnectionDetector
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    var location_lay : RelativeLayout? = null
    var location_text : TextView? = null
    var location_weather : LinearLayout? = null
    var temperature_text : TextView? = null
    var min_max_temperature_text : TextView? = null
    var weather_remark : TextView? = null
    var add_new_city : AppCompatImageView? = null
    var add_new_city_text : TextView? = null
    var searchLay : LinearLayout? = null
    var current_city_weather_lay : RelativeLayout? = null
    var recyclerView : RecyclerView? = null
    var mainScrollView : ScrollView? = null
    var dataSet = ArrayList<PrefsModel>()
    var mAdapter = SavedCityAdapter(dataSet)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        location_lay = findViewById(R.id.location_lay)
        location_text = findViewById(R.id.location_text)
        location_weather = findViewById(R.id.location_weather)
        temperature_text = findViewById(R.id.temperature_text)
        min_max_temperature_text = findViewById(R.id.min_max_temperature_text)
        weather_remark = findViewById(R.id.weather_remark)
        add_new_city = findViewById(R.id.add_new_city)
        add_new_city_text = findViewById(R.id.add_new_city_text)
        searchLay = findViewById(R.id.searchLay)
        current_city_weather_lay = findViewById(R.id.current_city_weather_lay)
        mainScrollView = findViewById(R.id.mainScrollView)
        recyclerView = findViewById(R.id.recyclerView)

        try{
            val mainViewModel : MainViewModel = ViewModelProviders.of(this@MainActivity).get(MainViewModel::class.java)

            recyclerView!!.isNestedScrollingEnabled = false
            recyclerView!!.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            recyclerView!!.adapter = mAdapter
            val listener = object: SavedCityAdapter.CustomViewHolderListener {
                override fun onCustomItemClicked(s: String, isCancel: Boolean, pos: Int) {
                    if(!isCancel){
                        if(ConnectionDetector().getConnectionDetector()!!.isConnectedToInternet(this@MainActivity)){
                            mainScrollView!!.fullScroll(ScrollView.FOCUS_UP);
                            mainViewModel.moveToTop(pos)
                            mainViewModel.fetchWeatherData(s, true)
                        }
                        else{
                            Toast.makeText(this@MainActivity, "No Internet Connection", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        mainViewModel.removePreference(pos)
                    }
                }
            }
            mAdapter.attachListener(listener)
            searchLay!!.setOnClickListener(View.OnClickListener {
                mainViewModel.clearCitySearch()
                val fragmenttransaction = supportFragmentManager.beginTransaction()
                val fragment = SearchFragment()
                fragmenttransaction.add(R.id.frameLayout, fragment,"search" ).addToBackStack("search")
                fragmenttransaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
                fragmenttransaction.commit()
            })
            add_new_city!!.setOnClickListener(View.OnClickListener {
                mainViewModel.addPreference()
            })
            mainViewModel.getPrefsData().observe(this, Observer<ArrayList<PrefsModel>>{ users ->
                try{
                    dataSet = users
                    mAdapter.updateDataset(dataSet)
                    mAdapter.notifyDataSetChanged()
                }
                catch (e: java.lang.Exception){
                    e.printStackTrace()
                }
            })
            mainViewModel.fetchWeather().observe(this, Observer<WeatherModel>{ users ->
                if(users!=null && users.name.isNotEmpty()){
                    current_city_weather_lay!!.visibility = View.VISIBLE
                    add_new_city!!.visibility = View.VISIBLE
                    add_new_city_text!!.visibility = View.VISIBLE
                    location_text!!.text = users.name
                    temperature_text!!.text = convertToCelsius(users!!.main!!.temp) + "°C"
                    min_max_temperature_text!!.text = convertToCelsius(users!!.main!!.temp_max) + "°C/" + convertToCelsius(users!!.main!!.temp_min) + "°C Feels like " + convertToCelsius(users!!.main!!.feels_like) + "°C"
                    weather_remark!!.text = users!!.weather!![0].description.replaceFirst(users!!.weather!![0].description[0], users!!.weather!![0].description[0].toUpperCase())
                }
                else{
                    current_city_weather_lay!!.visibility = View.GONE
                    add_new_city!!.visibility = View.GONE
                    add_new_city_text!!.visibility = View.GONE
                }
            })
        }
        catch (e : Exception) {
            e.printStackTrace()
        }
    }

    fun convertToCelsius(temp : Double): String{
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.CEILING
        return df.format(temp-273.15)
    }
}