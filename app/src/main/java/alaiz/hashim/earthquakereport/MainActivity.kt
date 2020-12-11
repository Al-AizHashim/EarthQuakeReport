package alaiz.hashim.earthquakereport

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity(),EarthQuakeFragment.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, EarthQuakeFragment.newInstance())
                .commit()
        }
    }

    override fun onEarthQuakeSelected(locationIntent: Intent) {
        if (locationIntent.resolveActivity(packageManager)!=null){
            startActivity(locationIntent)
        }
    }


}