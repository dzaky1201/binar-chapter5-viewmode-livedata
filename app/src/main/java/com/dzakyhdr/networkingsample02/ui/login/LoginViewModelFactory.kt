package com.dzakyhdr.networkingsample02.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dzakyhdr.networkingsample02.utils.SharedPreference

class LoginViewModelFactory(
    private val sharedPreference: SharedPreference
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(sharedPreference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}