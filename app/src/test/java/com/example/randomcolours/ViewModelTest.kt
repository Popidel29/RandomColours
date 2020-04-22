package com.example.randomcolours

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.randomcolours.common.model.ColoursWordEntity
import com.example.randomcolours.common.model.State
import com.example.randomcolours.random_colours.RandomColours
import com.example.randomcolours.repository.ColoursRepositoryImpl
import com.example.randomcolours.viewmodel.ColoursWordViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.RuntimeException


class ViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    private lateinit var coloursWordViewModel: ColoursWordViewModel

    @MockK
    private lateinit var coloursRepositoryImpl: ColoursRepositoryImpl

    @MockK
    private lateinit var randomColours: RandomColours

    @SpyK
    var responseObserver = Observer<State> {}

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        coloursWordViewModel = spyk((ColoursWordViewModel(coloursRepositoryImpl, randomColours)))
        coloursWordViewModel.state.observeForever(responseObserver)
    }

    @Test
    fun `Testing ViewModel when server responses with data`() {
        //Setup
        val repositoryNames = listOf("test0", "test1", "test2", "test3", "test4")
        val colour = "#FFFFFF"

        val entities = listOf(
            ColoursWordEntity("test0", colour), ColoursWordEntity("test1", colour)
            , ColoursWordEntity("test2", colour), ColoursWordEntity("test3", colour),
            ColoursWordEntity("test4", colour)
        )
        val result = State.OnSuccess(entities)


        //given
        coEvery {
            coloursRepositoryImpl.getColoursWordFromApi(5)
        } returns repositoryNames

        coEvery {
            randomColours.generateHexadecimal()
        } returns colour

        //when
        coloursWordViewModel.loadColours(5)

        //Then
        verify { responseObserver.onChanged(State.Loading) }
        verify { responseObserver.onChanged(result) }
    }

    @Test
    fun `Testing ViewModel when server responses with error`() {
        //Setup
        val error = "errorMessage"

        val result = State.OnFailure(error)


        //given
        coEvery {
            coloursRepositoryImpl.getColoursWordFromApi(5)
        } throws RuntimeException(error)

        //when
        coloursWordViewModel.loadColours(5)

        //Then
        verify { responseObserver.onChanged(State.Loading) }
        verify { responseObserver.onChanged(result) }
    }
}

