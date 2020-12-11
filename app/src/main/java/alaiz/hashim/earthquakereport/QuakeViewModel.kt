package alaiz.hashim.earthquakereport

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class QuakeViewModel : ViewModel() {

    val quakeItemLiveData: LiveData<List<QuakeItem>>

    init {
        quakeItemLiveData = QuakeFetchr().fetchQuakes()
    }
}