package com.example.randomcolours.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.randomcolours.repository.ColoursRepositoryInterface
import javax.inject.Inject

class ColoursWordViewModelFactory @Inject constructor(
    private val repository: ColoursRepositoryInterface
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ColoursWordViewModel(repository) as T
    }

}