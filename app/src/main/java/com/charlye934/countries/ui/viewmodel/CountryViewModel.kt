package com.charlye934.countries.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.charlye934.countries.data.model.Country
import com.charlye934.countries.data.repository.ContriesRepository
import com.charlye934.countries.utils.CheckInternetConnection
import com.charlye934.countries.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.NullPointerException
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val repositoryCountry: ContriesRepository,
    private val app: Application
) : ViewModel() {

    //private val disposable = CompositeDisposable()//Nos ayudara a agrupar y desecharnos de los observadores que tiene agregados
    private val _countries = MutableLiveData<NetworkResult<List<Country>>>()
    val countries: LiveData<NetworkResult<List<Country>>>
        get() = _countries

    fun refresh(){
        fetchCountries()
    }

    private fun fetchCountries(){
        viewModelScope.launch {
            _countries.postValue(NetworkResult.Loading())
            repositoryCountry.getCountries().collect {
                try {
                    if(CheckInternetConnection.hasInternetConnection(app))
                        _countries.postValue(it)
                    else
                        _countries.postValue(NetworkResult.Error("Error internet conection"))
                }catch (e: Exception){
                    _countries.postValue(NetworkResult.Error(e.message.toString()))
                }
            }
        }
    }

    /*private fun fetchCountries(){
        _loading.value = true
        disposable.add(
            repositoryCountry.getContries()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(value: List<Country>) {
                        _countries.value = value
                        _countryLoadError.value = false
                        _loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        _countryLoadError.value = true
                        _loading.value = false
                    }
                })
        )
    }*/

}