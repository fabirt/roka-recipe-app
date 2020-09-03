package com.fabirt.roka.features.categories.presentation.view_model

import android.os.Handler
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
        Handler().postDelayed({
            _categories.value = categoriesList
        }, 350)
    }
}