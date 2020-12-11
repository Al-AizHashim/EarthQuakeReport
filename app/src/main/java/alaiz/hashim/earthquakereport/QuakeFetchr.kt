package alaiz.hashim.earthquakereport


import alaiz.hashim.api.EarthQuakeResponse
import alaiz.hashim.api.QuakeApi
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "QuakeFetchr"

class QuakeFetchr{

    private val quakeApi: QuakeApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://earthquake.usgs.gov/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        quakeApi = retrofit.create(QuakeApi::class.java)
    }

    fun fetchQuakes(): LiveData<List<QuakeItem>> {
        val responseLiveData: MutableLiveData<List<QuakeItem>> = MutableLiveData()
        val quakeRequest: Call<EarthQuakeResponse> = quakeApi.fetchQuakes()

        quakeRequest.enqueue(object : Callback<EarthQuakeResponse> {

            override fun onFailure(call: Call<EarthQuakeResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch quakes",t)
            }

            override fun onResponse(call: Call<EarthQuakeResponse>, response: Response<EarthQuakeResponse>) {
                Log.d(TAG, "Response received corr")
                val quakeResponse: EarthQuakeResponse? = response.body()
                var quakeItems: List<QuakeItem> = quakeResponse?.quakeItems
                    ?: mutableListOf()
                responseLiveData.value = quakeItems
            }
        })

        return responseLiveData
    }
}