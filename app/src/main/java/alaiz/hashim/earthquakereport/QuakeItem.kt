package alaiz.hashim.earthquakereport

import com.google.gson.annotations.SerializedName

data class QuakeItem (

    @SerializedName("properties")
    var properties: QuakeProperties = QuakeProperties(),

    @SerializedName("geometry")
    var geo: QuakeGeo = QuakeGeo()
)