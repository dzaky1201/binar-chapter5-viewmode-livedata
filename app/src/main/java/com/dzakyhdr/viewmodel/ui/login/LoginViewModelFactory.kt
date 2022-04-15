package com.dzakyhdr.viewmodel.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dzakyhdr.viewmodel.utils.SharedPreference

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