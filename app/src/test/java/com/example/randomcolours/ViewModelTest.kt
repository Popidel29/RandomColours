package com.example.randomcolours

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.colours_ao_test.MainCoroutinesRule
import com.example.randomcolours.common.model.ColoursWordEntity
import com.example.randomcolours.common.model.Response
import com.example.randomcolours.random_colours.RandomColours
import com.example.randomcolours.repository.ColoursRepositoryImpl
import com.example.randomcolours.repository.ColoursRepositoryInterface
import com.example.randomcolours.viewmodel.ColoursWordViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    private lateinit var coloursWordViewModel: ColoursWordViewModel

    @MockK
    private lateinit var coloursRepositoryImpl: ColoursRepositoryImpl

    @MockK
    private lateinit var randomColours: RandomColours

    @SpyK
    var responseObserver = Observer<Response>{}

    @Before
    fun setUp(){
        MockKAnnotations.init(this)

        coloursWordViewModel = spyk((ColoursWordViewModel(coloursRepositoryImpl,randomColours)))
        coloursWordViewModel.response.observeForever(responseObserver)
    }

    @Test
    fun  `Testing ViewModel when server responses with data`(){
        //Setup
        val result = Response.ONSUCCESS(listOf<ColoursWordEntity>())

        //given
        coEvery {
            coloursRepositoryImpl.getColoursWordFromApi(5)
        } returns listOf("test0,test1,test2,teste3,teste4")

        coEvery {
            randomColours.generateHexadecimal()
        } returns "#FFFFFF"

        //when
        coloursWordViewModel.loadColours(5)

        //Then
        verify { responseObserver.onChanged(result)}

        var i : Int = 0
        var expectedResult : Boolean = true

        for (item in result.listOfWords)
        {
            if(item.colourName != "test"+i || item.hexadecimal != "#FFFFFF")
            {
                expectedResult = false
            }
            i++
        }

        Assert.assertTrue(expectedResult)
    }
}