package com.example.randomcolours.dagger.module

import com.example.randomcolours.common.BASE_URL
import com.example.randomcolours.common.network.ColoursWordClient
import com.example.randomcolours.random_colours.RandomColours
import com.example.randomcolours.repository.ColoursRepositoryImpl
import com.example.randomcolours.repository.ColoursRepositoryInterface
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        @Provides
        @Singleton
        fun provideColoursWordClient(retrofit: Retrofit): ColoursWordClient {
            return retrofit.create(ColoursWordClient::class.java)

        }

        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
        }

        @Provides
        @Singleton
        fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
            return OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
        }

        @Provides
        @Singleton
        fun provideLoggingInterceptor(): HttpLoggingInterceptor{
            return  HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }
    }
}