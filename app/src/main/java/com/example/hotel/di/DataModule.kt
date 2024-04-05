package com.example.hotel.di

import com.example.hotel.data.repository.NetworkRepositoryImpl
import com.example.hotel.domain.repositories.NetworkRepository
import com.example.hotel.data.remote.RetrofitApi
import com.example.hotel.data.remote.RetrofitInstance
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun providePersonApi(): RetrofitApi {
        return RetrofitInstance.service
    }

    @Provides
    fun provideNetworkRepository(service: RetrofitApi): NetworkRepository{
        return NetworkRepositoryImpl(service)
    }

}