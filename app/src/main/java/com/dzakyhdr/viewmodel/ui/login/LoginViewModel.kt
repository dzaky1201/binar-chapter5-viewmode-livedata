package com.dzakyhdr.viewmodel.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dzakyhdr.viewmodel.data.auth.LoginRequest
import com.dzakyhdr.viewmodel.data.model.LoginResponseItem
import com.dzakyhdr.viewmodel.data.network.ApiClient
import com.dzakyhdr.viewmodel.utils.SharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(
    private val sharedPreference: SharedPreference
) : ViewModel() {
    private var email = ""
    private var password = ""

    private var _moveToHome = MutableLiveData<Boolean>()
    val moveToHome get() = _moveToHome

    private var _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status


    fun login() {
        val login = LoginRequest(email, password)
        ApiClient.instance.loginUser(login).enqueue(object : Callback<LoginResponseItem> {
            override fun onResponse(
                call: Call<LoginResponseItem>,
                response: Response<LoginResponseItem>
            ) {
                if (response.isSuccessful) {
                    _status.value = true
                    val body = response.body()
                    sharedPreference.saveKey(body?.email!!, body.password!!)
                    sharedPreference.saveKeyState(true)
                } else {
                    _status.value = false
                }

            }

            override fun onFailure(call: Call<LoginResponseItem>, t: Throwable) {

            }
        })
    }

    fun loginUserName(email: String, password: String) {
        this.email = email
        this.password = password
    }

    fun setDefault() {
        _status.value = false
    }

    fun loginSession() {
        _moveToHome.value = sharedPreference.getPrefKeyStatus("login_status")
    }





}