package com.example.randomcolours.repository

interface ColoursRepositoryInterface {
    suspend fun getColoursWordFromApi(numberOfWord: Int): List<String>
}