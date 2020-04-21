package com.example.randomcolours.repository

import com.example.randomcolours.common.network.ColoursWordClient
import javax.inject.Inject

class ColoursRepositoryImpl @Inject constructor(
    private val coloursWordClient: ColoursWordClient) : ColoursRepositoryInterface
{
    override suspend fun getColoursWordFromApi(numberOfWord: Int): List<String> {
        return coloursWordClient.getRandomColoursNames(numberOfWord)
    }
}