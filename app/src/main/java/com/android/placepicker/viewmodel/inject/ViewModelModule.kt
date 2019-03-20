package com.android.placepicker.viewmodel.inject

import android.arch.lifecycle.ViewModel
import com.android.placepicker.viewmodel.PlaceConfirmDialogViewModel
import com.android.placepicker.viewmodel.PlacePickerViewModel
import com.android.placepicker.viewmodel.inject.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(PlacePickerViewModel::class)
    abstract fun bindPlacePickerViewModel(viewViewModel: PlacePickerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PlaceConfirmDialogViewModel::class)
    abstract fun bindPlaceConfirmDialogViewModel(viewViewModel: PlaceConfirmDialogViewModel): ViewModel
}
