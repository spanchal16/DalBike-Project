package com.CSCI5708.dalbike

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.CSCI5708.dalbike.model.Bikes
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_layout.view.*
import kotlin.reflect.KFunction1


class BikeAdapter(
    private val context: Context,
    private val navigateToBooking: KFunction1<@ParameterName(name = "bike") Bikes, Unit>
) :
    RecyclerView.Adapter<BikeAdapter.BikeViewHolder>() {
    val bikes = mutableListOf<Bikes>()

    inner class BikeViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Bikes): Unit {
            view.bikeName.text = data.bikeName
            view.bikeDescription.text = data.bikeDescription
            Picasso.get()
                .load(data.bikeUrl)
//                .placeholder(R.drawable.bike1)
                .error(R.drawable.bike4)
                .into(view.image_view)

            view.bikeAction.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            view.bikeAction.setOnClickListener {
                navigateToBooking(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BikeViewHolder {
        return BikeViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return bikes.size
    }

    override fun onBindViewHolder(holder: BikeViewHolder, position: Int) {
        holder.bind(bikes.get(position))
    }
}
