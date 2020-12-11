package alaiz.hashim.earthquakereport

import com.google.gson.annotations.SerializedName

class QuakeGeo (
    @SerializedName("coordinates")
    var geos: List<Double> = emptyList()
)