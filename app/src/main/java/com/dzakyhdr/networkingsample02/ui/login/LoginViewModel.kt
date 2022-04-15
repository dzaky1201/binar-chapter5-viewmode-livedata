package com.dzakyhdr.networkingsample02.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dzakyhdr.networkingsample02.data.auth.LoginRequest
import com.dzakyhdr.networkingsample02.data.model.LoginResponseItem
import com.dzakyhdr.networkingsample02.data.network.ApiClient
import com.dzakyhdr.networkingsample02.utils.SharedPreference
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
                val body = response.body()

                if (response.isSuccessful) {
                    _status.value = true
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

    fun setDefault(){
        _status.value = false
    }

    fun loginSession(){
        _moveToHome.value = sharedPreference.getPrefKeyStatus("login_status")
    }



}