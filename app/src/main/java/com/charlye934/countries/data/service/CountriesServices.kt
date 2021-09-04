package com.charlye934.countries.data.service

import android.util.Log
import com.charlye934.countries.data.model.Country
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class CountriesServices @Inject constructor(private val api: CountriesApi) {

    suspend fun getCountries(): Response<List<Country>> {
        return api.getCountries()
    }
    //fun getCountrires(): Single<List<Country>>{ return api.getContries()}
}