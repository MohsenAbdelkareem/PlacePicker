package com.android.placepicker.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task

import java.io.IOException
import java.util.Locale

object LocationUtils {

    private const val REQUEST_CHECK_SETTINGS = 503

    @SuppressLint("MissingPermission")
    fun displayLocationSettingsRequest(context: Context, locationListener: LocationListener) {

        val mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        val locationRequest = LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = locationRequest?.let {
            LocationSettingsRequest.Builder()
                .addLocationRequest(it)
        }
        builder?.setAlwaysShow(true)

        val client: SettingsClient = LocationServices.getSettingsClient(context)
        val result: Task<LocationSettingsResponse> = client.checkLocationSettings(builder?.build())


        val mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult == null) {
                    locationListener.onLocationError()
                    return
                }
                for (location in locationResult.locations) {
                    // Update UI with location data
                    // ...
                    if (location != null) {
                        locationListener.onLocationChange(location)
                        return
                    }
                }
            }
        }

        result.addOnSuccessListener {
            // All location settings are satisfied. The client can initialize
            // location requests here.

            mFusedLocationClient.requestLocationUpdates(
                locationRequest,
                mLocationCallback, null
            )

            mFusedLocationClient.lastLocation
                .addOnSuccessListener(context as Activity) { location ->
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
                        locationListener.onLocationChange(location)
                    } else {
                        locationListener.onLocationError()
                    }
                }
        }

        result.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    exception.startResolutionForResult(
                        context as Activity?,
                        REQUEST_CHECK_SETTINGS
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                    locationListener.onLocationError()
                }
            }
        }

//        result.addOnCompleteListener { task ->
//            try {
//                val response = task.getResult(ApiException::class.java)
//                if (response != null) {
//                    response.locationSettingsStates
//
//                    mFusedLocationClient.requestLocationUpdates(
//                        locationRequest,
//                        mLocationCallback, null
//                    )
//
//                    mFusedLocationClient.lastLocation
//                        .addOnSuccessListener(context as Activity) { location ->
//                            // Got last known location. In some rare situations this can be null.
//                            if (location != null) {
//                                mFusedLocationClient.removeLocationUpdates(mLocationCallback)
//                                locationListener.onLocationChange(location)
//                            } else {
//                                locationListener.onLocationError()
//                            }
//                        }
//                }
//            } catch (exception: ApiException) {
//                when (exception.statusCode) {
//                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->
//                        // Location settings are not satisfied. But could be fixed by showing the
//                        // user a dialog.
//                        try {
//                            // Cast to a resolvable exception.
//                            val resolvable = exception as ResolvableApiException
//                            // Show the dialog by calling startResolutionForResult(),
//                            // and check the result in onActivityResult().
//                            resolvable.startResolutionForResult(
//                                context as Activity,
//                                REQUEST_CHECK_SETTINGS
//                            )
//                        } catch (e: IntentSender.SendIntentException) {
//                            // Ignore the error.
//                            locationListener.onLocationError()
//                        } catch (e: Throwable) {
//                            locationListener.onLocationError()
//                        }
//
//                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE ->
//                        // Location settings are not satisfied. However, we have no way to fix the
//                        // settings so we won't show the dialog.
//                        locationListener.onLocationError()
//                }
//            }
//        }.addOnCanceledListener { locationListener.onLocationError() }
//            .addOnFailureListener { locationListener.onLocationError() }
    }

    fun getAddressFromLocation(context: Context, location: Location): Array<String?> {

        val geocode = Geocoder(context, Locale.getDefault())
        val addresses: List<Address>
        val address = arrayOfNulls<String>(2)
        try {
            addresses = geocode.getFromLocation(location.latitude, location.longitude, 1)
            if (addresses.isNotEmpty()) {
                address[0] = addresses[0].thoroughfare
                address[1] = addresses[0].getAddressLine(0)
                //get current province/City
                val province = addresses[0].adminArea

                //get country
                val country = addresses[0].countryName
                Log.v("LocationUtils", "$province / $country")
            } else {
                address[0] = null
                address[1] = null
            }
        } catch (e: IOException) {
            address[0] = null
            address[1] = null
            e.printStackTrace()
        }

        return address
    }

    interface LocationListener {

        fun onLocationChange(location: Location?)

        fun onLocationError()
    }

}
