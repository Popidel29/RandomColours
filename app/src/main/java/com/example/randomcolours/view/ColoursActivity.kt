package com.example.randomcolours.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.randomcolours.ColoursApplication
import com.example.randomcolours.R
import com.example.randomcolours.common.COLOURS_WORDS
import com.example.randomcolours.common.SHARED_PREFERENCES_COLOURS_KEY
import com.example.randomcolours.common.model.ColoursWordEntity
import com.example.randomcolours.dagger.component.DaggerMainActivityComponent
import com.example.randomcolours.dagger.module.ColoursViewModelModule
import com.example.randomcolours.viewmodel.ColoursWordViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Type
import javax.inject.Inject

class ColoursActivity : AppCompatActivity() {

    @Inject
    lateinit var wordViewModel: ColoursWordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        DaggerMainActivityComponent.builder().coloursApplicationComponent(
            (application as ColoursApplication).getApplicationComponent()).coloursViewModelModule(
            ColoursViewModelModule(this)).build().inject(this)


        val coloursList = getDataFromSharedPreferences()
        if(coloursList.isNotEmpty())
        {
            DisplayColours(coloursList)
        }

        initiateLiveData()

        btn_change_colour.setOnClickListener {
            wordViewModel.loadColours(COLOURS_WORDS)
        }
    }

    private fun initiateLiveData()
    {
        wordViewModel.colours.observe(this, Observer {
            saveDataToSharedPreferences(it)
            DisplayColours(it)
        })

        wordViewModel.errorMessage.observe(this, Observer {

        })
    }

    private fun getDataFromSharedPreferences() : List<ColoursWordEntity>
    {
        val preferences = this.getPreferences(Context.MODE_PRIVATE)
        val json : String = preferences.getString(SHARED_PREFERENCES_COLOURS_KEY, null)
            ?: return arrayListOf()

        return  convertFromStringOfGsonToListOfColoursEntity(json)

    }

    private fun saveDataToSharedPreferences(coloursList : List<ColoursWordEntity>)
    {
        val preferences = this.getPreferences(Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(SHARED_PREFERENCES_COLOURS_KEY,
            convertFromListOfColoursEntityToStringOfGson(coloursList))
    }

    private fun DisplayColours(coloursList : List<ColoursWordEntity>)
    {
        rv_colours.apply {
            adapter = ColoursAdapter(coloursList)
            layoutManager = LinearLayoutManager(this@ColoursActivity)
            visibility = View.VISIBLE
        }
    }

    private fun convertFromStringOfGsonToListOfColoursEntity(gsonList : String) : List<ColoursWordEntity>
    {
        val gson = Gson()
        val type: Type = object : TypeToken<List<ColoursWordEntity>>() {}.type
        return gson.fromJson(gsonList,type)
    }

    private fun convertFromListOfColoursEntityToStringOfGson(coloursEntityList :List<ColoursWordEntity>) : String
    {
        val gson = Gson()
        return gson.toJson(coloursEntityList)
    }
}
