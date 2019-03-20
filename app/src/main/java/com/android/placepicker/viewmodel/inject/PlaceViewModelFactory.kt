package com.android.placepicker.viewmodel.inject

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class PlaceViewModelFactory @Inject constructor(
        private val mViewModels: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        var factoryProvider: Provider<ViewModel>? = null

        for ((key, value) in mViewModels) {
            if (modelClass.isAssignableFrom(key)) {
                factoryProvider = value
                break
            }
        }

        factoryProvider?.let {
            return it.get() as T
        }

        throw IllegalArgumentException("Unknown ViewModel class name: $modelClass")
    }
}
