package com.example.randomcolours.dagger.module

import androidx.lifecycle.ViewModelProvider
import com.example.randomcolours.common.network.ColoursWordClient
import com.example.randomcolours.dagger.MainActivityScope
import com.example.randomcolours.repository.ColoursRepositoryImpl
import com.example.randomcolours.repository.ColoursRepositoryInterface
import com.example.randomcolours.view.ColoursActivity
import com.example.randomcolours.viewmodel.ColoursWordViewModel
import com.example.randomcolours.viewmodel.ColoursWordViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ColoursViewModelModule(private val coloursActivity: ColoursActivity) {

    @Provides
    @MainActivityScope
    fun provideColoursWordViewModel(coloursWordViewModelFactory: ColoursWordViewModelFactory): ColoursWordViewModel{
        return ViewModelProvider(coloursActivity,coloursWordViewModelFactory).get(ColoursWordViewModel::class.java)
    }


    @Provides
    @MainActivityScope
    fun providesColoursRepository(coloursWordClient: ColoursWordClient): ColoursRepositoryInterface{
        return ColoursRepositoryImpl(coloursWordClient)
    }
}