package com.example.hotel.di

import com.example.hotel.network.RetrofitApi
import com.example.hotel.network.RetrofitInstance
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun providePersonApi(): RetrofitApi {
        return RetrofitInstance.service
    }



}