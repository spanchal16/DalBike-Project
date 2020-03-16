package com.CSCI5708.dalbike

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_bike_list.*

class BikeListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Toast.makeText(context, "Bike List", Toast.LENGTH_SHORT).show()
        return inflater.inflate(R.layout.fragment_bike_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            it.getSerializable("bike") as Bike
        }

        with(viewPager) {
            adapter = BikeAdapter(context, this@BikeListFragment::navigateToBooking).also {
                it.bikes.addAll(DummyTest.bikeList)
            }
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    fun navigateToBooking(bike: Bike) {
        findNavController().navigate(R.id.action_bikeListFragment_to_bookBikeFragment, bundleOf("bike" to bike))
    }
}
