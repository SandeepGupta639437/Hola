//package com.example.hola
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//
//class HomePageViewModel : ViewModel() {
//
//    private val _homePageLiveData = MutableLiveData<List<HomePageResponse>>()
//    val homePageLiveData: LiveData<List<HomePageResponse>> = _homePageLiveData
//
//    fun fetchHomePageDetails() {
//        viewModelScope.launch {
//            try {
//                val response = withContext(Dispatchers.IO) {
//                    // Assuming you have a Retrofit instance
//                    homePageApi.getHomePageDetails()
//                }
//
//                if (response.isSuccessful) {
//                    _homePageLiveData.value = response.body() // This will hold the list of HomePageResponse
//                } else {
//                    // Handle error response
//                }
//            } catch (e: Exception) {
//                // Handle network or other errors
//            }
//        }
//    }
//}
