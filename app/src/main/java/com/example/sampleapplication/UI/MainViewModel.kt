package com.example.sampleapplication.UI

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sampleapplication.Model.CityModel
import com.example.sampleapplication.Model.PrefsModel
import com.example.sampleapplication.Model.WeatherModel
import com.example.sampleapplication.Network.GetWeatherData
import com.example.sampleapplication.Network.RetrofitClientInstance
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val weather = MutableLiveData<WeatherModel>()
    val cityList =  ArrayList<String>()
    var cityListTemp = MutableLiveData<ArrayList<String>>()
    val SAVED_CITIES = "savedCities"
    private val savedCityData = ArrayList<PrefsModel>()
    private val savedCityDataLiveData = MutableLiveData<ArrayList<PrefsModel>>()
    private val sharedPreferences: SharedPreferences = application.getSharedPreferences(SAVED_CITIES,
        Context.MODE_PRIVATE)
    val gson = Gson()

    init {
        loadJSONFromAsset(application)
        fetchPreferences()
    }

    fun fetchWeatherData(city : String, isFromFavourite : Boolean) : LiveData<WeatherModel> {
        cityListTemp.value = ArrayList<String>()
        val linkedHashMap : LinkedHashMap<String, String> = LinkedHashMap();
        linkedHashMap.put("q", city)
        linkedHashMap.put("appid", "7cddc0579bdfc05d0977b0d1b2a3e82a")
        val getWeatherData : GetWeatherData = RetrofitClientInstance().getRetrofitInstance()!!.create(GetWeatherData::class.java)
        val call : Call<WeatherModel?>? = getWeatherData.getWeatherData(linkedHashMap)
        call!!.enqueue(object : Callback<WeatherModel?> {
            override fun onResponse(
                call: Call<WeatherModel?>?,
                response: Response<WeatherModel?>
            ) {
                weather.value = response.body()
                if(isFromFavourite){
                    updateTop()
                }

            }

            override fun onFailure(
                call: Call<WeatherModel?>,
                t: Throwable
            ) {

            }
        })
        return weather
    }

    fun fetchWeather() : LiveData<WeatherModel> {
        return weather
    }

    fun loadJSONFromAsset(context: Context): String {
        var json: String = ""
        try {
            val inputStream = context.assets.open("current.city.list.min.json")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer)
            val jsonArray = JSONArray(json)
            if(jsonArray.length()>0){
                for(i in 1..jsonArray.length()){
                    val jsonObject = jsonArray.get(i-1) as JSONObject
                    val cityModel = CityModel(
                        jsonObject.optInt("id"),
                        jsonObject.optString("name"),
                        jsonObject.optString("state"),
                        jsonObject.optString("country")
                    )
                    cityList.add(jsonObject.optString("name"))
                }
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return json
    }

    fun fetchPreferences(){
        val gson = Gson()
        savedCityData.clear()
        if (sharedPreferences.getString(SAVED_CITIES, "")!!.isNotEmpty()) {
            val jsonArray = JSONArray(sharedPreferences.getString(SAVED_CITIES, ""))
            for(i in 1..jsonArray.length()){
                val model = gson.fromJson(jsonArray[i-1].toString(), PrefsModel::class.java)
                savedCityData.add(model)
            }
            savedCityDataLiveData.value = savedCityData
        } else {

        }
    }

    fun getPrefsData() : LiveData<ArrayList<PrefsModel>> {
        return savedCityDataLiveData
    }

    fun addPreference(){
        if(sharedPreferences.getString(SAVED_CITIES, "")!!.isNotEmpty()){
            val jsonArray = JSONArray(sharedPreferences.getString(SAVED_CITIES, ""))
            if(jsonArray.toString().contains("\"id\\\":"+weather.value!!.id.toString()+",")){
                Toast.makeText(getApplication(), "City already saved in Favourite", Toast.LENGTH_SHORT).show()
                return
            }
            else{
                val prefsModel = PrefsModel(weather.value!!.weather, weather.value!!.main, weather.value!!.id, weather.value!!.name, weather.value!!.sys, weather.value!!.dt)
                savedCityData.add(prefsModel)
                savedCityDataLiveData.value = savedCityData
                val json = gson.toJson(prefsModel)
                jsonArray.put(json)
                val editor = sharedPreferences.edit()
                editor.putString(SAVED_CITIES, jsonArray.toString())
                editor.commit()
            }
        }
        else{
            val prefsModel = PrefsModel(weather.value!!.weather, weather.value!!.main, weather.value!!.id, weather.value!!.name, weather.value!!.sys, weather.value!!.dt)
            savedCityData.add(prefsModel)
            savedCityDataLiveData.value = savedCityData
            val jsonArray = JSONArray()
            val json = gson.toJson(prefsModel)
            jsonArray.put(json)
            val editor = sharedPreferences.edit()
            editor.putString(SAVED_CITIES, jsonArray.toString())
            editor.commit()
        }
    }

    fun removePreference(pos : Int){
        if(sharedPreferences.getString(SAVED_CITIES, "")!!.isNotEmpty()){
            val jsonArray = JSONArray(sharedPreferences.getString(SAVED_CITIES, ""))
            savedCityData.removeAt(pos)
            savedCityDataLiveData.value = savedCityData
            jsonArray.remove(pos)
            val editor = sharedPreferences.edit()
            editor.putString(SAVED_CITIES, jsonArray.toString())
            editor.commit()
        }
    }

    fun moveToTop(pos: Int){
        if(sharedPreferences.getString(SAVED_CITIES, "")!!.isNotEmpty()){
            val jsonArray = JSONArray(sharedPreferences.getString(SAVED_CITIES, ""))
            val modelSelected = savedCityData[pos]
            savedCityData.removeAt(pos)
            savedCityData.add(modelSelected)
            savedCityDataLiveData.value = savedCityData
            val json = jsonArray[pos]
            jsonArray.remove(pos)
            jsonArray.put(json)
            val editor = sharedPreferences.edit()
            editor.putString(SAVED_CITIES, jsonArray.toString())
            editor.commit()
        }
    }

    fun updateTop(){
        if(sharedPreferences.getString(SAVED_CITIES, "")!!.isNotEmpty()){
            val prefsModel = PrefsModel(weather.value!!.weather, weather.value!!.main, weather.value!!.id, weather.value!!.name, weather.value!!.sys, weather.value!!.dt)
            val jsonArray = JSONArray(sharedPreferences.getString(SAVED_CITIES, ""))
            val modelSelected = prefsModel
            savedCityData.removeAt(savedCityData.size-1)
            savedCityData.add(modelSelected)
            savedCityDataLiveData.value = savedCityData
            val json = gson.toJson(prefsModel)
            jsonArray.remove(jsonArray.length()-1)
            jsonArray.put(json)
            val editor = sharedPreferences.edit()
            editor.putString(SAVED_CITIES, jsonArray.toString())
            editor.commit()
        }
    }

    fun searchCityInArray(toString: String){
        try{
            if(toString.isNotEmpty()){
                toString.toLowerCase(Locale.getDefault())
                val arrayList =  ArrayList<String>()
                for(i in 0 until cityList.size-1){
                    if(cityList[i].toLowerCase(Locale.getDefault()).contains(toString)){
                        arrayList.add(cityList[i])
                    }
                }
                cityListTemp.value = arrayList
            }
            else{
                cityListTemp.value = ArrayList<String>()
            }
        }
        catch (e : Exception){
            e.printStackTrace()
        }
    }

    fun clearCitySearch(){
        cityListTemp.value = ArrayList<String>()
    }

    fun getCitySearch() : LiveData<ArrayList<String>> {
        return cityListTemp
    }

}