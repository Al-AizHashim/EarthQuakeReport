package alaiz.hashim.earthquakereport

import android.os.Bundle
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

private const val TAG = "QuakeFragment"

class EarthQuakeFragment : Fragment() {
    private lateinit var quakeViewModel: QuakeViewModel
    private lateinit var quakeRecyclerView: RecyclerView

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

    private class QuakeHolder(itemTextView: View) : RecyclerView.ViewHolder(itemTextView) {
        private val erarthQuakeMagnitudeTV =
            itemView.findViewById(R.id.magnitude_text_view) as TextView
        private val erarthQuakePlaceTV = itemView.findViewById(R.id.place_view) as TextView
        fun bind(quakeItems: QuakeItem) {
            erarthQuakeMagnitudeTV.setText(quakeItems.properties.magnitude)
            erarthQuakePlaceTV.setText(quakeItems.properties.place)

        }


    }

    private class QuakeAdapter(private val quakeItems: List<QuakeItem>) :
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

    companion object {
        fun newInstance() = EarthQuakeFragment()
    }
}

