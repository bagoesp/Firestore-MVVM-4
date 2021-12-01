package com.bugs.fbmvvm3.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bugs.fbmvvm3.repositories.SampelRepository

class MainViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(SampelRepository::class.java)
            .newInstance(SampelRepository())
    }
}