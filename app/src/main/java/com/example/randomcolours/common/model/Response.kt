package com.example.randomcolours.common.model

sealed class Response {
    object VALUE : Response()
    data class ONSUCCESS(val listOfWords : List<ColoursWordEntity>) : Response()
    data class ONFAILURE(val errorMessage : String?) : Response()
}