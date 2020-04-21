package com.example.randomcolours.dagger.component

import com.example.randomcolours.ColoursApplication
import com.example.randomcolours.common.network.ColoursWordClient
import com.example.randomcolours.dagger.module.ColoursViewModelModule
import com.example.randomcolours.dagger.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ColoursViewModelModule::class])
interface ColoursApplicationComponent {

    fun getNetworkClient() : ColoursWordClient

    fun inject(application: ColoursApplication)


}