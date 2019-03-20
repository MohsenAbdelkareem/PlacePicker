package com.android.placepicker.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GeocodeResult(
        val results: List<SimplePlace>,
        val status: String
)