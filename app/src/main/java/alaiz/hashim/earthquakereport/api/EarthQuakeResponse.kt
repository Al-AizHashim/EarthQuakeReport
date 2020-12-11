package alaiz.hashim.api

import alaiz.hashim.earthquakereport.QuakeItem
import com.google.gson.annotations.SerializedName

class EarthQuakeResponse (
      @SerializedName("features")
     var quakeItems: List<QuakeItem>
)