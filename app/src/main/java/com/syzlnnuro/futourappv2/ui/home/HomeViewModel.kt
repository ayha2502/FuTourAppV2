package com.syzlnnuro.futourappv2.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.syzlnnuro.futourappv2.ApiConfig
import com.syzlnnuro.futourappv2.PlaceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _categories = MutableLiveData<List<PlaceResponse>>()
    val categories: LiveData<List<PlaceResponse>> get() = _categories

    private val _recommendedLocations = MutableLiveData<List<PlaceResponse>>()
    val recommendedLocations: LiveData<List<PlaceResponse>> get() = _recommendedLocations

    private val _bestLocations = MutableLiveData<List<PlaceResponse>>()
    val bestLocations: LiveData<List<PlaceResponse>> get() = _bestLocations

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun fetchPlaces() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getPlaces(active = 1)
        client.enqueue(object : Callback<PlaceResponse> {
            override fun onResponse(call: Call<PlaceResponse>, response: Response<PlaceResponse>)  {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let { place ->
                        // Sesuaikan logika pengolahan data
                    }
                } else {
                    _error.value = "Failed to load place"
                }
            }

            override fun onFailure(call: Call<PlaceResponse>, t: Throwable) {
                _isLoading.value = false
                _error.value = t.message
            }
        })
    }
}
