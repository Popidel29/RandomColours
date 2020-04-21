package com.example.randomcolours.dagger.component

import com.example.randomcolours.dagger.MainActivityScope
import com.example.randomcolours.dagger.module.ColoursViewModelModule
import com.example.randomcolours.dagger.module.NetworkModule
import com.example.randomcolours.view.ColoursActivity
import dagger.Component

@MainActivityScope
@Component(
    modules = [ColoursViewModelModule::class],
    dependencies = [ColoursApplicationComponent::class]
)
interface MainActivityComponent {
    fun inject (coloursActivity: ColoursActivity)

}