package alaiz.hashim.earthquakereport

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Layout
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "QuakeFragment"

class EarthQuakeFragment : Fragment() {


    interface Callbacks {
        fun onEarthQuakeSelected(locationIntent: Intent)
    }

    private var callbacks: Callbacks? = null
    var  locationList:List<Double> = emptyList()
    private lateinit var quakeViewModel: QuakeViewModel
    private lateinit var quakeRecyclerView: RecyclerView
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        quakeViewModel =
            ViewModelProviders.of(this).get(QuakeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_earth_quake, container, false)
        quakeRecyclerView = view.findViewById(R.id.quake_recycler_view)
        quakeRecyclerView.layoutManager = GridLayoutManager(context, 1)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quakeViewModel.quakeItemLiveData.observe(
            viewLifecycleOwner,
            Observer { quakeItems ->
                Log.d(TAG, "Have quake items from view model $quakeItems")
                updateui(quakeItems)
            })


    }

    private fun updateui(quakeItems: List<QuakeItem>) {
        quakeRecyclerView.adapter = QuakeAdapter(quakeItems)
    }

    private inner class QuakeHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        @SuppressLint("SimpleDateFormat")
        var dateFormatter: SimpleDateFormat = SimpleDateFormat("EE, MM d, yyyy")

        @SuppressLint("SimpleDateFormat")
        var timeFormatter: SimpleDateFormat = SimpleDateFormat("hh:mm a")
        private val erarthQuakeMagnitudeTV =
            itemView.findViewById(R.id.magnitude_text_view) as TextView
        private val erarthQuakeCountryTV = itemView.findViewById(R.id.country_textView) as TextView
        private val erarthQuakeTimeTV = itemView.findViewById(R.id.time_textView) as TextView
        private val erarthQuakeDateTV = itemView.findViewById(R.id.date_textView) as TextView
        private val erarthQuakeAddressTV = itemView.findViewById(R.id.address_view) as TextView


        fun bind(quakeItems: QuakeItem) {
            val Compound_Place = quakeItems.properties.place
            val addressList = Compound_Place.split(" of ")
            val address = addressList[0]
            val country = addressList[1]
             locationList =quakeItems.geo.geos
            val time = quakeItems.properties.time
            erarthQuakeTimeTV.text = timeFormatter.format(time)
            erarthQuakeDateTV.text = dateFormatter.format(time)
            var quakeMagnitude: Double = quakeItems.properties.magnitude.toDouble()
            quakeMagnitude = Math.round(quakeMagnitude * 10.0) / 10.0
            erarthQuakeMagnitudeTV.apply {
                text = quakeMagnitude.toString()
                when {
                    quakeMagnitude < 4.0 -> setBackgroundResource(R.drawable.green_circle_shape)
                    quakeMagnitude < 5.0 -> setBackgroundResource(R.drawable.yellow_circle_shape)
                    quakeMagnitude < 6.0 -> setBackgroundResource(R.drawable.orange_circle_shape)
                    quakeMagnitude >= 6.0 -> setBackgroundResource(R.drawable.red_circle_shape)
                }
            }

            erarthQuakeCountryTV.setText(country)
            erarthQuakeAddressTV.setText(address)


        }


        override fun onClick(v: View?) {
            val longitude=locationList[0]
            val latitude=locationList[1]

            val locationIntent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse("geo:$longitude,$latitude")
            }

            callbacks?.onEarthQuakeSelected(locationIntent)
        }

    }

    private inner class QuakeAdapter(private val quakeItems: List<QuakeItem>) :
        RecyclerView.Adapter<QuakeHolder>() {


        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): QuakeHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.quake_list_item, parent, false)
            return QuakeHolder(view)
        }

        override fun getItemCount(): Int = quakeItems.size

        override fun onBindViewHolder(holder: QuakeHolder, position: Int) {
            val quakeItems = quakeItems[position]
            holder.bind(quakeItems)

        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    companion object {
        fun newInstance() = EarthQuakeFragment()
    }
}

