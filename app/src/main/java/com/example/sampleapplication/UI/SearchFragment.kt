package com.example.sampleapplication.UI

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapplication.R
import com.example.sampleapplication.Utils.ConnectionDetector

class SearchFragment : Fragment() {

    var back : AppCompatImageView? = null
    var search_text : AppCompatEditText? = null
    var recyclerView : RecyclerView? = null
    var dataSet = ArrayList<String>()
    var mAdapter = SearchFragmentAdapter(dataSet)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.search_fragment_layout, container, false)
        recyclerView = rootView.findViewById(R.id.recyclerView)
        back = rootView.findViewById(R.id.back)
        search_text = rootView.findViewById(R.id.search_text)
        back!!.setOnClickListener(View.OnClickListener {
            closeKeyboard()
            activity!!.onBackPressed()
        })
        val mainViewModel : MainViewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)
        recyclerView!!.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView!!.adapter = mAdapter
        val listener = object: SearchFragmentAdapter.CustomViewHolderListener {
            override fun onCustomItemClicked(s: String) {
                if(ConnectionDetector().getConnectionDetector()!!.isConnectedToInternet(activity!!.applicationContext)){
                    closeKeyboard()
                    mainViewModel.fetchWeatherData(s, false)
                    activity!!.onBackPressed()
                }
                else{
                    Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show()
                }
            }
        }
        mAdapter.attachListener(listener)
        search_text!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!=null){
                    mainViewModel.searchCityInArray(s.toString().toLowerCase())
                    mAdapter.updateSearchString(s.toString())
                }
            }
        })
        search_text!!.requestFocus()
        showKeyboard()
        mainViewModel.getCitySearch().observe(this, Observer<ArrayList<String>>{ users ->
            try{
                dataSet = users
                mAdapter.updateDataset(dataSet)
                mAdapter.notifyDataSetChanged()
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        })
        return rootView
    }

    fun showKeyboard() {
        val inputMethodManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    fun closeKeyboard() {
        val inputMethodManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }
}