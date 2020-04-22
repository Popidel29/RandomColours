package com.example.randomcolours.common.model

sealed class State {
    object Loading :State()
    data class OnSuccess(val listOfWords : List<ColoursWordEntity>) : State()
    data class OnFailure(val errorMessage : String?) : State()
}