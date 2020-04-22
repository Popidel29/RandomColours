package com.example.randomcolours.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomcolours.common.model.ColoursWordEntity
import com.example.randomcolours.common.model.Response
import com.example.randomcolours.random_colours.RandomColours
import com.example.randomcolours.repository.ColoursRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ColoursWordViewModel(
    private val repository: ColoursRepositoryInterface,
    private val randomColoursGenerator: RandomColours
) : ViewModel() {

    private var responseLiveData = MutableLiveData<Response>()

    val response: LiveData<Response>
        get() = responseLiveData


    fun loadColours(numberOfWord: Int) {
        responseLiveData.value = Response.VALUE
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val namesList: List<String> = repository.getColoursWordFromApi(numberOfWord)
                val colorsListEntity = ArrayList<ColoursWordEntity>()

                for (item in namesList) {
                    colorsListEntity.add(
                        (ColoursWordEntity(
                            item,
                            randomColoursGenerator.generateHexadecimal()
                        ))
                    )
                }

                responseLiveData.postValue(Response.ONSUCCESS(colorsListEntity))


            } catch (exception: Exception) {
                if (exception.message != null) {
                    responseLiveData.postValue(Response.ONFAILURE(exception.message))
                } else {
                    responseLiveData.postValue(Response.ONFAILURE("No error Message!"))
                }
            }
        }
    }
}
