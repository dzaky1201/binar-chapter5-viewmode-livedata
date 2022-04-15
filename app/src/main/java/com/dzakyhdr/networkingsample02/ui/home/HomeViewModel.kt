package com.dzakyhdr.networkingsample02.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dzakyhdr.networkingsample02.data.model.CarResponseItem
import com.dzakyhdr.networkingsample02.data.network.ApiClient
import com.dzakyhdr.networkingsample02.data.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {
    private var _cars =  MutableLiveData<List<CarResponseItem>>()
    val cars: LiveData<List<CarResponseItem>> get() = _cars

    private var _loading = MutableLiveData<Boolean>()
    val loading get() = _loading


    init {
        getCar()
    }

    private fun getCar(){
        ApiClient.instance.getAllCar().enqueue(object : Callback<List<CarResponseItem>>{
            override fun onResponse(
                call: Call<List<CarResponseItem>>,
                response: Response<List<CarResponseItem>>
            ) {
                if (response.isSuccessful){
                    _cars.value = response.body()
                    _loading.value = false
                } else {
                    _loading.value = true
                }
            }

            override fun onFailure(call: Call<List<CarResponseItem>>, t: Throwable) {
                _loading.value = true
            }
        })
    }
}