package com.bugs.fbmvvm3.main

import androidx.lifecycle.ViewModel
import com.bugs.fbmvvm3.model.Sampel
import com.bugs.fbmvvm3.repositories.SampelRepository

class MainViewModel(private val repository: SampelRepository): ViewModel() {
    fun getAllSampel() = repository.getAllSampel()

    fun addSampel(sampel: Sampel) = repository.addSampel(sampel)

    fun deleteSampel(sampel: Sampel) = repository.deleteSampel(sampel)
}