package com.example.randomcolours.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.randomcolours.ColoursApplication
import com.example.randomcolours.R
import com.example.randomcolours.common.COLOURS_WORDS
import com.example.randomcolours.common.SHARED_PREFERENCES_COLOURS_KEY
import com.example.randomcolours.common.model.ColoursWordEntity
import com.example.randomcolours.common.model.Response
import com.example.randomcolours.dagger.component.DaggerMainActivityComponent
import com.example.randomcolours.dagger.module.ColoursViewModelModule
import com.example.randomcolours.viewmodel.ColoursWordViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.lang.reflect.Type
import javax.inject.Inject

class ColoursActivity : AppCompatActivity() {

    @Inject
    lateinit var wordViewModel: ColoursWordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        DaggerMainActivityComponent.builder().coloursApplicationComponent(
            (application as ColoursApplication).getApplicationComponent()
        ).coloursViewModelModule(
            ColoursViewModelModule(this)
        ).build().inject(this)


        val coloursList = getDataFromSharedPreferences()
        if (coloursList.isNotEmpty()) {
            displayColours(coloursList)
        }

        initiateLiveData()

        btn_change_colour.setOnClickListener {
            progressBarShow()
            wordViewModel.loadColours(COLOURS_WORDS)
        }
    }

    private fun initiateLiveData() {
        wordViewModel.response.observe(this, Observer {
            when(it)
            {
                is Response.ONSUCCESS -> {
                    saveDataToSharedPreferences(it.listOfWords)
                    displayColours(it.listOfWords)
                    hideErrorMessage()
                }

                is Response.ONFAILURE -> {
                    displayErrorMessage(it.errorMessage)
                }
            }
        })
    }

    private fun getDataFromSharedPreferences(): List<ColoursWordEntity> {
        val preferences = this.getPreferences(Context.MODE_PRIVATE)
        val json: String = preferences.getString(SHARED_PREFERENCES_COLOURS_KEY, null)
            ?: return arrayListOf()

        return convertFromStringOfGsonToListOfColoursEntity(json)

    }

    private fun saveDataToSharedPreferences(coloursList: List<ColoursWordEntity>) {
        val preferences = this.getPreferences(Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(
            SHARED_PREFERENCES_COLOURS_KEY,
            convertFromListOfColoursEntityToStringOfGson(coloursList)
        )

        editor.apply()
    }

    private fun progressBarShow() {
        pb_loading_colours.visibility = View.VISIBLE
    }

    private fun progressBarHide() {
        pb_loading_colours.visibility = View.GONE
    }

    private fun displayColours(coloursList: List<ColoursWordEntity>) {
        rv_colours.apply {
            adapter = ColoursAdapter(coloursList)
            layoutManager = LinearLayoutManager(this@ColoursActivity)
            visibility = View.VISIBLE
        }
        progressBarHide()
    }
    private fun displayErrorMessage(errorMessgae : String?){
        rv_colours.visibility = GONE
        tv_errorMessage.text = errorMessgae
        tv_errorMessage.visibility = VISIBLE
        progressBarHide()
    }

    private fun hideErrorMessage(){
        rv_colours.visibility = VISIBLE
        tv_errorMessage.text = ""
        tv_errorMessage.visibility = GONE
    }

    private fun convertFromStringOfGsonToListOfColoursEntity(gsonList: String): List<ColoursWordEntity> {
        try{
            val gson = Gson()
            val type: Type = object : TypeToken<List<ColoursWordEntity>>() {}.type
            return gson.fromJson(gsonList, type)
        }
        catch(exception:Exception){
            displayErrorMessage("Error converting from Gson")
            return  listOf<ColoursWordEntity>()
        }

    }

    private fun convertFromListOfColoursEntityToStringOfGson(coloursEntityList: List<ColoursWordEntity>): String {
        try{
            val gson = Gson()
            return gson.toJson(coloursEntityList)
        }
        catch(exception: Exception){
            displayErrorMessage("Error converting to Gson")
            return "Message"
        }

    }
}
