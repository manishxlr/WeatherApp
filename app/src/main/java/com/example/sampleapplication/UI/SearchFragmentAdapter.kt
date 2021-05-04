package com.example.sampleapplication.UI

import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapplication.R
import java.lang.Exception

class SearchFragmentAdapter(private var dataSet: ArrayList<String>) :
    RecyclerView.Adapter<SearchFragmentAdapter.ViewHolder>() {

    interface CustomViewHolderListener{
        fun onCustomItemClicked(s : String)
    }

    var listener = object: CustomViewHolderListener {
        override fun onCustomItemClicked(s: String) {
        }
    }

    var searchString = ""

    fun updateDataset(dataSet: ArrayList<String>){
        this.dataSet = dataSet
    }

    fun updateSearchString(search: String){
        this.searchString = search
    }

    fun attachListener(listener: CustomViewHolderListener){
        this.listener = listener
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView

        init {
            textView = view.findViewById(R.id.text)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.search_cityitem, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        try{
            val spannable: Spannable = SpannableString(dataSet[position])
            val i = dataSet[position].indexOf(searchString, 0, true)
            spannable.setSpan(
                ForegroundColorSpan(Color.BLACK),
                i,
                i+searchString.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannable.setSpan(
                StyleSpan(Typeface.BOLD),
                i,
                i+searchString.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            viewHolder.textView.text = spannable
        }
        catch (e : Exception){
            viewHolder.textView.text = ""
            e.printStackTrace()
        }
        viewHolder.textView.setOnClickListener(View.OnClickListener {
            listener.onCustomItemClicked(dataSet[position])
        })
    }

    override fun getItemCount() = dataSet.size

}