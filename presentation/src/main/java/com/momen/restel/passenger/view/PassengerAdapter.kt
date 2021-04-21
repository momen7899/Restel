package com.momen.restel.passenger.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.momen.restel.R
import com.momen.restel.databinding.PassengerItemBinding
import com.momen.restel.passenger.model.PassengerModel

class PassengerAdapter : RecyclerView.Adapter<PassengerAdapter.PassengerViewHolder>() {

    private val items = ArrayList<PassengerModel>()

    class PassengerViewHolder(val passenger: PassengerItemBinding) :
        RecyclerView.ViewHolder(passenger.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassengerViewHolder =
        PassengerViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.passenger_item, parent, false
            ) as PassengerItemBinding
        )

    override fun onBindViewHolder(holder: PassengerViewHolder, position: Int) {
        holder.passenger.passenger = items[position]
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: ArrayList<PassengerModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

}
