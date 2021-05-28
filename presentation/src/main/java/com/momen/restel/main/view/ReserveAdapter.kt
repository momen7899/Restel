package com.momen.restel.main.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.momen.restel.R
import com.momen.restel.databinding.ReservationItemBinding
import com.momen.restel.main.model.ReserveModel

class ReserveAdapter(private val fragment: MainFragment) :
    RecyclerView.Adapter<ReserveAdapter.ReserveViewHolder>() {

    private val items = ArrayList<ReserveModel>()

    class ReserveViewHolder(val reserve: ReservationItemBinding) :
        RecyclerView.ViewHolder(reserve.root) {
        val edit: ImageView = reserve.root.findViewById(R.id.reservationItemEdit)
        val remove: ImageView = reserve.root.findViewById(R.id.reservationItemRemove)
    }

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

        holder.edit.setOnClickListener {
            fragment.showBottomSheet(true, items[position])
        }

        holder.remove.setOnClickListener {
            fragment.showDelMsg(items[position], position)
            removeItem(items[position])
        }

    }

    override fun getItemCount(): Int = items.size

    private fun removeItem(reserve: ReserveModel) {
        items.remove(reserve)
        notifyDataSetChanged()
    }

    fun nextId(): Int? {
        return if (items.isEmpty()) 0
        else items[items.size - 1].id?.plus(1)
    }

    fun setItems(items: ArrayList<ReserveModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(reserve: ReserveModel, position: Int) {
        items.add(position, reserve)
        notifyDataSetChanged()
    }

    fun filterItems(items: ArrayList<ReserveModel>, search: String) {
        val list = ArrayList<ReserveModel>()
        items.forEach {
            if (it.customer?.contains(search) == true || it.client?.contains(search) == true
                || it.room?.contains(search) == true || it.price?.toString()
                    ?.contains(search) == true
            )
                list.add(it)
        }
        setItems(list)
    }
}