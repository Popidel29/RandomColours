package com.example.randomcolours.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomcolours.common.model.ColoursWordEntity
import com.example.randomcolours.random_colours.RandomColours
import com.example.randomcolours.repository.ColoursRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ColoursWordViewModel(private val repository: ColoursRepositoryInterface) : ViewModel() {

    private val colourList = MutableLiveData<List<ColoursWordEntity>>()
    private val randonColoursGenerator = RandomColours.create()

    val colours: LiveData<List<ColoursWordEntity>>
        get() = colourList


    private val mErrorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = mErrorMessage

    fun loadColours(numberOfWord: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val namesList : List<String>  = repository.getColoursWordFromApi(numberOfWord)
                val colorsListEntity = ArrayList<ColoursWordEntity>()

                for (item in namesList) {
                   colorsListEntity.add((ColoursWordEntity(item,
                       randonColoursGenerator.generateHexadecimal())))
                }

                colourList.postValue(colorsListEntity)
            } catch (exception: Exception) {
                mErrorMessage.postValue(exception.message)
            }
        }
    }

}