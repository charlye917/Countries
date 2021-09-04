package com.charlye934.countries.data.repository

import com.charlye934.countries.data.model.Country
import com.charlye934.countries.data.service.CountriesServices
import com.charlye934.countries.utils.BaseApiResponse
import com.charlye934.countries.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ContriesRepository @Inject constructor(
    private val apiCountries: CountriesServices
): BaseApiResponse(){

    suspend fun getCountries(): Flow<NetworkResult<List<Country>>> {
        return flow<NetworkResult<List<Country>>> {
            emit(safeApiCall { apiCountries.getCountries() })
        }.flowOn(Dispatchers.IO)
    }
    //fun getContries(): Single<List<Country>> { return apiCountries.getCountrires() }
}