package com.dzakyhdr.viewmodel.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dzakyhdr.viewmodel.utils.SharedPreference

class HomeViewModelFactory(private val sharedPreference: SharedPreference) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(sharedPreference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}