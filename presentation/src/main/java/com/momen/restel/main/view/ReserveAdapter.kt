package com.momen.restel.main.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.momen.restel.R
import com.momen.restel.databinding.ReservationItemBinding
import com.momen.restel.main.model.ReserveModel

class ReserveAdapter : RecyclerView.Adapter<ReserveAdapter.ReserveViewHolder>() {

    private val items = ArrayList<ReserveModel>()

    class ReserveViewHolder(val reserve: ReservationItemBinding) :
        RecyclerView.ViewHolder(reserve.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReserveViewHolder =
        ReserveViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.reservation_item, parent, false
            ) as ReservationItemBinding
        )

    override fun onBindViewHolder(holder: ReserveViewHolder, position: Int) {
        val currentReserve: ReserveModel = items[position]
        holder.reserve.reserve = currentReserve
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: ArrayList<com.momen.restel.main.model.ReserveModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}