package com.dzakyhdr.viewmodel.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dzakyhdr.viewmodel.data.model.CarResponseItem
import com.dzakyhdr.viewmodel.data.network.ApiClient
import com.dzakyhdr.viewmodel.utils.SharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val sharedPreference: SharedPreference) : ViewModel() {
    private var _cars = MutableLiveData<List<CarResponseItem>>()
    val cars: LiveData<List<CarResponseItem>> get() = _cars

    private var _loading = MutableLiveData<Boolean>()
    val loading get() = _loading

    private var _email = MutableLiveData<String>()
    val email get() = _email


    fun getCar() {
        ApiClient.instance.getAllCar().enqueue(object : Callback<List<CarResponseItem>> {
            override fun onResponse(
                call: Call<List<CarResponseItem>>,
                response: Response<List<CarResponseItem>>
            ) {
                if (response.isSuccessful) {
                    _cars.value = response.body()
                    _loading.value = false
                    _email.value = sharedPreference.getPrefKey("email")
                } else {
                    _loading.value = true
                }
            }

            override fun onFailure(call: Call<List<CarResponseItem>>, t: Throwable) {
                _loading.value = true
            }
        })
    }

    fun logOut() {
        sharedPreference.clearUsername()
        _email.value = ""
    }

    override fun onCleared() {
        super.onCleared()
    }
}