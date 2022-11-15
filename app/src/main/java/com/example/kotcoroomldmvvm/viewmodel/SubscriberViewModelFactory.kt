package com.example.kotcoroomldmvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotcoroomldmvvm.repos.SubscriberRepository

class SubscriberViewModelFactory(private val repo : SubscriberRepository)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SubscriberViewModel::class.java)){
            return SubscriberViewModel(repo) as T
        }
        throw java.lang.IllegalArgumentException("Unknown viewmodel class")
    }
}