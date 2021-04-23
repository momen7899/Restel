package com.momen.restel.room.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.momen.restel.R
import com.momen.restel.databinding.RoomItemBinding
import com.momen.restel.room.model.RoomModel

class RoomAdapter : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    private val items = ArrayList<RoomModel>()

    class RoomViewHolder(val room: RoomItemBinding) : RecyclerView.ViewHolder(room.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder =
        RoomViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.room_item, parent, false
            ) as RoomItemBinding
        )

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.room.room = items[position]
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: ArrayList<RoomModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

}
