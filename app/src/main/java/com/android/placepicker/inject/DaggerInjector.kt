package com.android.placepicker.inject

import android.app.Application

class DaggerInjector private constructor() {

    companion object {

        private var injector: PlaceComponent? = null

        private fun buildComponent(application: Application): PlaceComponent {
            return DaggerPlaceComponent.builder()
                    .androidModule(AndroidModule(application)).build()
        }

        fun getInjector(application: Application): PlaceComponent {
            return injector ?: buildComponent(application)
        }
    }
}