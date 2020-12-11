package alaiz.hashim.api

import retrofit2.Call
import retrofit2.http.GET

interface QuakeApi {
    @GET("fdsnws/event/1/query?format=geojson&limit=10")
    fun fetchQuakes(): Call<EarthQuakeResponse>
}