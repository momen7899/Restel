package com.momen.restel.main.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.momen.restel.R
import com.momen.restel.reserve.model.ReserveModel

class ReserveAdapter : RecyclerView.Adapter<ReserveAdapter.ReserveViewHolder>() {

    private val items = ArrayList<ReserveModel>()

    class ReserveViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val customer: TextView = view.findViewById(R.id.reservationItemCustomer)
        val client: TextView = view.findViewById(R.id.reservationItemClient)
        val room: TextView = view.findViewById(R.id.reservationItemRoomName)
        val price: TextView = view.findViewById(R.id.reservationItemPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReserveViewHolder =
        ReserveViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.reservation_item, parent, false)
        )

    override fun onBindViewHolder(holder: ReserveViewHolder, position: Int) {
        with(holder) {
            customer.text = items[position].customer
            client.text = items[position].client
            room.text = items[position].room
            price.text = items[position].price.toString().plus(" تومان")
        }
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: ArrayList<ReserveModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}