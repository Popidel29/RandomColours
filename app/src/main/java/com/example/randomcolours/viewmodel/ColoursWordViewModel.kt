package com.example.randomcolours.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomcolours.common.model.ColoursWordEntity
import com.example.randomcolours.common.model.State

import com.example.randomcolours.random_colours.RandomColours
import com.example.randomcolours.repository.ColoursRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ColoursWordViewModel(
    private val repository: ColoursRepositoryInterface,
    private val randomColoursGenerator: RandomColours
) : ViewModel() {

    private var _state = MutableLiveData<State>()

    val state: LiveData<State>
        get() = _state


    fun loadColours(numberOfWord: Int) {
        _state.value = State.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val names: List<String> = repository.getColoursWordFromApi(numberOfWord)
                val colorsListEntity = ArrayList<ColoursWordEntity>()

                for (item in names) {
                    colorsListEntity.add(
                        (ColoursWordEntity(
                            item,
                            randomColoursGenerator.generateHexadecimal()
                        ))
                    )
                }

                _state.postValue(State.OnSuccess(colorsListEntity))


            } catch (exception: Exception) {
                if (exception.message != null) {
                    _state.postValue(State.OnFailure(exception.message))
                } else {
                    _state.postValue(State.OnFailure("No error Message!"))
                }
            }
        }
    }
}
