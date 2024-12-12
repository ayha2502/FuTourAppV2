package com.syzlnnuro.futourappv2.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syzlnnuro.futourappv2.data.ApiConfig
import com.syzlnnuro.futourappv2.data.ListofPlaceResponse
import com.syzlnnuro.futourappv2.searchApi.SearchApiConfig
import com.syzlnnuro.futourappv2.searchData.SearchRequest
import com.syzlnnuro.futourappv2.searchData.SearchResponseItem
import kotlinx.coroutines.launch
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

    private val _searchResults = MutableLiveData<List<SearchResponseItem>>()
    val searchResults: LiveData<List<SearchResponseItem>> get() = _searchResults

    fun searchPlaces(description: String) {
        _isLoading.value = true
        val request = SearchRequest(description)

        viewModelScope.launch {
            try {
                val response = SearchApiConfig().getSearchApiService("token").search(request)
                if (response.searchResponse != null) {
                    _searchResults.value = response.searchResponse?.filterNotNull() ?: emptyList()
                } else {
                    _error.value = "No results found"
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchPlaces() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getPlaces(active = 1)
        client.enqueue(object : Callback<List<ListofPlaceResponse>> {
            override fun onResponse(
                call: Call<List<ListofPlaceResponse>>,
                response: Response<List<ListofPlaceResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let { places ->
                        _categories.value = places // Contoh: Menyimpan hasil ke _categories
                    }
                } else {
                    _error.value = "Failed to load places"
                }
            }

            override fun onFailure(call: Call<List<ListofPlaceResponse>>, t: Throwable) {
                _isLoading.value = false
                _error.value = t.message
            }
        })
    }
}
