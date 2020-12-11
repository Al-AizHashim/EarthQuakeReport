package alaiz.hashim.earthquakereport

import com.google.gson.annotations.SerializedName

class QuakeProperties (
    @SerializedName("place")var place: String = "",
    @SerializedName("time") var time: Long  = 0L,
    @SerializedName("mag") var magnitude : String = ""
    )