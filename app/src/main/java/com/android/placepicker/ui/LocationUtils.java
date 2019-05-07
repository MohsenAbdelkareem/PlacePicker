package com.android.placepicker.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.*;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public final class LocationUtils {

    public static final int REQUEST_CHECK_SETTINGS = 503;

    @SuppressLint("MissingPermission")
    public static void displayLocationSettingsRequest(Context context , LocationListener locationListener) {

        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

//        Task<LocationSettingsResponse> result =
//                LocationServices.getSettingsClient(context).checkLocationSettings(builder.build());
//
//        LocationCallback mLocationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                if (locationResult == null) {
//                    locationListener.onLocationError();
//                    return;
//                }
//                for (Location location : locationResult.getLocations()) {
//                    // Update UI with location data
//                    // ...
//                    locationListener.onLocationChange(location);
//                    if (location != null){
//                        return;
//                    }
//                }
//            }
//        };
//
//
//        result.addOnCompleteListener(task -> {
//            try {
//                LocationSettingsResponse response = task.getResult(ApiException.class);
//                assert response != null;
//                response.getLocationSettingsStates();
//
//                mFusedLocationClient.requestLocationUpdates(locationRequest,
//                        mLocationCallback,
//                        null);
//
//                mFusedLocationClient.getLastLocation()
//                        .addOnSuccessListener((Activity)context, location -> {
//                            // Got last known location. In some rare situations this can be null.
//                            if (location != null) {
//                                mFusedLocationClient.removeLocationUpdates(mLocationCallback);
//                                locationListener.onLocationChange(location);
//                            }else {
//                                locationListener.onLocationError();
//                            }
//                        });
//
//            } catch (ApiException exception) {
//                switch (exception.getStatusCode()) {
//                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                        // Location settings are not satisfied. But could be fixed by showing the
//                        // user a dialog.
//                        try {
//                            // Cast to a resolvable exception.
//                            ResolvableApiException resolvable = (ResolvableApiException) exception;
//                            // Show the dialog by calling startResolutionForResult(),
//                            // and check the result in onActivityResult().
//                            resolvable.startResolutionForResult(
//                                    (Activity) context,
//                                    REQUEST_CHECK_SETTINGS);
//                        } catch (IntentSender.SendIntentException | ClassCastException e) {
//                            // Ignore the error.
//                            locationListener.onLocationError();
//                        }
//                        break;
//                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                        // Location settings are not satisfied. However, we have no way to fix the
//                        // settings so we won't show the dialog.
//                        locationListener.onLocationError();
//                        break;
//                }
//            }
//        }).addOnCanceledListener(locationListener::onLocationError)
//        .addOnFailureListener(e -> locationListener.onLocationError());
    }

    public static String[] getAddressFromLocation(Context context , Location location){

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses;
        String[] address = new String[2];
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (! addresses.isEmpty()){
                address[0] = addresses.get(0).getThoroughfare();
                address[1] = addresses.get(0).getAddressLine(0);
                //get current province/City
                String province = addresses.get(0).getAdminArea();

                //get country
                String country = addresses.get(0).getCountryName();
                Log.v("LocationUtils", province + " / " + country);
            }else {
                address[0] = null;
                address[1] = null;
            }
        } catch (IOException e) {
            address[0] = null;
            address[1] = null;
            e.printStackTrace();
        }

        return address;
    }

    public interface LocationListener{

        void onLocationChange(Location location);

        void onLocationError();
    }

}
