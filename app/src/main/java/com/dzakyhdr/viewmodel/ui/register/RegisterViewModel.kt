package com.dzakyhdr.viewmodel.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dzakyhdr.viewmodel.data.auth.RegisterRequest
import com.dzakyhdr.viewmodel.data.model.RegisterResponseItem
import com.dzakyhdr.viewmodel.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    private var username: String? = null
    private var password: String? = null

    private var _showSnackbar = MutableLiveData<Boolean>()
    val showSnackbar get() = _showSnackbar

    private var _loading = MutableLiveData<Boolean>()
    val loading get() = _loading

    fun registerResponse() {
        val register = RegisterRequest(username, password, "admin")

        ApiClient.instance.registerUser(register).enqueue(object : Callback<RegisterResponseItem> {
            override fun onResponse(
                call: Call<RegisterResponseItem>,
                response: Response<RegisterResponseItem>
            ) {
                _loading.value = true
                if (response.code() == 201){
                    _showSnackbar.value = true
                    _loading.value = false
                } else if (response.code() == 400){
                    _showSnackbar.value = false
                    _loading.value = false
                }

            }

            override fun onFailure(call: Call<RegisterResponseItem>, t: Throwable) {
                _showSnackbar.value = true
            }
        })
    }

    fun registerRequest(username: String, password: String) {
        this.username = username
        this.password = password
    }

    override fun onCleared() {
        super.onCleared()
        _showSnackbar.value = false
    }
}