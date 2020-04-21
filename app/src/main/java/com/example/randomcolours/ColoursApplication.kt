package com.example.randomcolours

import android.app.Application
import com.example.randomcolours.dagger.component.ColoursApplicationComponent
import com.example.randomcolours.dagger.component.DaggerColoursApplicationComponent


class ColoursApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

    fun getApplicationComponent(): ColoursApplicationComponent {
        return DaggerColoursApplicationComponent.builder().build()
    }

}