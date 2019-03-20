package com.android.placepicker.inject

import com.android.placepicker.repository.inject.RepositoryModule
import com.android.placepicker.ui.PlaceConfirmDialogFragment
import com.android.placepicker.ui.PlacePickerActivity
import com.android.placepicker.viewmodel.inject.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidModule::class,
            ViewModelModule::class,
            RepositoryModule::class
        ]
)
interface PlaceComponent {

    fun inject(activity: PlacePickerActivity)
    fun inject(fragment: PlaceConfirmDialogFragment)
}