package com.example.sampleapplication.UI

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapplication.Model.PrefsModel
import com.example.sampleapplication.R
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class SavedCityAdapter(private var dataSet: ArrayList<PrefsModel>) :
    RecyclerView.Adapter<SavedCityAdapter.ViewHolder>() {


    interface CustomViewHolderListener{
        fun onCustomItemClicked(s : String, isCancel : Boolean, pos: Int)
    }

    var listener = object: CustomViewHolderListener {
        override fun onCustomItemClicked(s: String, isCancel : Boolean, pos: Int) {
        }
    }

    fun updateDataset(dataSet: ArrayList<PrefsModel>){
        this.dataSet = dataSet
    }

    fun attachListener(listener: CustomViewHolderListener){
        this.listener = listener
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val city_name: TextView
        val maxmintemp: TextView
        val time: TextView
        val temp: TextView
        val super_lay : CardView
        val cancel : AppCompatImageView

        init {
            city_name = view.findViewById(R.id.city_name)
            maxmintemp = view.findViewById(R.id.maxmintemp)
            time = view.findViewById(R.id.time)
            temp = view.findViewById(R.id.temp)
            super_lay = view.findViewById(R.id.super_lay)
            cancel = view.findViewById(R.id.cancel)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.home_saved_city_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        try{
            viewHolder.city_name.text = dataSet[itemCount-1-position].name
            viewHolder.city_name.text = dataSet[itemCount-1-position].name
            viewHolder.time.text = formatDate(dataSet[itemCount-1-position].dt)
            viewHolder.temp.text = convertToCelsius(dataSet[itemCount-1-position]!!.main!!.temp) + "°C"
            viewHolder.maxmintemp.text = convertToCelsius(dataSet[itemCount-1-position]!!.main!!.temp_max) + "°C/" + convertToCelsius(dataSet[itemCount-1-position]!!.main!!.temp_min) + "°C Feels like " + convertToCelsius(dataSet[itemCount-1-position]!!.main!!.feels_like) + "°C"
        }
        catch (e : Exception){
            e.printStackTrace()
        }
        viewHolder.super_lay.setOnClickListener(View.OnClickListener {
            listener.onCustomItemClicked(dataSet[itemCount-1-position].name, false, itemCount-1-position)
        })
        viewHolder.cancel.setOnClickListener(View.OnClickListener {
            listener.onCustomItemClicked("", true, itemCount-1-position)
        })
    }

    override fun getItemCount() = dataSet.size

    private fun convertToCelsius(temp : Double): String{
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.CEILING
        return df.format(temp-273.15)
    }

    private fun formatDate(millisecond : Long) : String{
        try{
            val sdf = SimpleDateFormat()
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            val sdf1 = SimpleDateFormat()
            sdf1.timeZone = TimeZone.getTimeZone("IST")
            val date = sdf1.parse(sdf.format(Date(millisecond*1000)))
            val sdf2 = SimpleDateFormat()
            return sdf2.format(date)
        }
        catch (e : java.lang.Exception){
            return ""
        }
    }

}