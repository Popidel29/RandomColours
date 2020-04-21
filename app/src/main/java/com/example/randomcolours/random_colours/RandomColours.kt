package com.example.randomcolours.random_colours

import kotlin.random.Random

class RandomColours {

    companion object Factory {
        fun create(): RandomColours = RandomColours()
    }

    fun randomNumbers() : Int{
        return Random.nextInt(15)

    }

    fun convertToHexadecimal(number: Int): Char {
        return when (number) {
            0 -> '0'
            1 -> '1'
            2 -> '2'
            3 -> '3'
            4 -> '4'
            5 -> '5'
            6 -> '6'
            7 -> '7'
            8 -> '8'
            9 -> '9'
            10 -> 'A'
            11 -> 'B'
            12 -> 'C'
            13 -> 'D'
            14 -> 'E'
            15 -> 'F'
            else -> 'G'
        }
    }

    fun generateHexadecimal(): String{
        val hexadecimalColours = StringBuilder()
        hexadecimalColours.append('#')

        for (i in 1..6) {
            hexadecimalColours.append(convertToHexadecimal(randomNumbers()))
        }
        return hexadecimalColours.toString()
    }
}