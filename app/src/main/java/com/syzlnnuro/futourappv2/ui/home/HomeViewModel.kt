package com.syzlnnuro.futourappv2.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.syzlnnuro.futourappv2.data.ApiConfig
import com.syzlnnuro.futourappv2.data.ListofPlaceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _categories = MutableLiveData<List<ListofPlaceResponse>>()
    val categories: LiveData<List<ListofPlaceResponse>> get() = _categories

    private val _recommendedLocations = MutableLiveData<List<ListofPlaceResponse>>()
    val recommendedLocations: LiveData<List<ListofPlaceResponse>> get() = _recommendedLocations

    private val _bestLocations = MutableLiveData<List<ListofPlaceResponse>>()
    val bestLocations: LiveData<List<ListofPlaceResponse>> get() = _bestLocations

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun fetchPlaces() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getPlaces(active = 1)
        client.enqueue(object : Callback<List<ListofPlaceResponse>>  {
            override fun onResponse(call: Call<List<ListofPlaceResponse>>, response: Response<List<ListofPlaceResponse>>)  {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val places = response.body() ?: emptyList()
                    // Misalkan kategori, rekomendasi, dan lokasi terbaik di-filter berdasarkan genre atau rating
                    _categories.value = places.filter { it.genre == "Category" }
                    _recommendedLocations.value = places.filter { it.rating ?: 0 >= 4 }
                    _bestLocations.value = places.sortedByDescending { it.rating }.take(5)
                } else {
                    _error.value = "Failed to load place"
                }
            }

            override fun onFailure(call: Call<List<ListofPlaceResponse>>, t: Throwable) {
                _isLoading.value = false
                _error.value = t.message
            }
        })
    }
}
