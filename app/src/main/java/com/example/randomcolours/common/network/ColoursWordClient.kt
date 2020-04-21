package com.example.randomcolours.common.network

import com.example.randomcolours.common.COLOUR_END_POINT
import retrofit2.http.GET
import retrofit2.http.Query

interface ColoursWordClient {

    @GET(COLOUR_END_POINT)
    suspend fun getRandomColoursNames(@Query("number") words: Int): List<String>
}