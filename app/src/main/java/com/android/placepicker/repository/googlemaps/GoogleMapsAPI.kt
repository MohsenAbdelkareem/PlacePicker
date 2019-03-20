package com.android.placepicker.repository.googlemaps

import com.android.placepicker.model.GeocodeResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleMapsAPI {

    @GET("geocode/json")
    fun findByLocation(@Query("latlng") location: String,
                       @Query("key") apiKey: String)
            : Single<GeocodeResult>
}