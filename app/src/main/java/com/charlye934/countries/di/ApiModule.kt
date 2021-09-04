package com.charlye934.countries.di

import com.charlye934.countries.data.service.CountriesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)//Para determinar el alcance, es decir cuanto durara la instancia
object ApiModule {

    private const val BASE_URL = "https://raw.githubusercontent.com"

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit{
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideCountriesApi(retrofit: Retrofit): CountriesApi{
        return retrofit.create(CountriesApi::class.java)
    }
}