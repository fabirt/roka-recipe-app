package com.fabirt.roka.features.categories.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fabirt.roka.features.categories.constants.categoriesList
import com.fabirt.roka.features.categories.domain.model.Category

class CategoriesViewModel : ViewModel() {
    private val _categories: MutableLiveData<List<Category>> = MutableLiveData()

    val categories: LiveData<List<Category>>
        get() = _categories

    init {
        _categories.value = categoriesList.toList()
    }
}
