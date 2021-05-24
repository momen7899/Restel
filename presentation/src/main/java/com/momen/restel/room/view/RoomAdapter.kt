package com.momen.restel.room.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.momen.restel.R
import com.momen.restel.databinding.RoomItemBinding
import com.momen.restel.room.model.RoomModel

class RoomAdapter(private val fragment: RoomFragment) :
    RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    private val items = ArrayList<RoomModel>()

    class RoomViewHolder(val room: RoomItemBinding) : RecyclerView.ViewHolder(room.root) {
        val edit: ImageView = room.root.findViewById(R.id.roomItemEdit)
        val remove: ImageView = room.root.findViewById(R.id.roomItemRemove)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder =
        RoomViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.room_item, parent, false
            ) as RoomItemBinding
        )

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {

        with(holder) {
            room.room = items[position]
            edit.setOnClickListener {
                fragment.showBottomSheet(true, items[position])
            }

            remove.setOnClickListener {
                fragment.showDelMsg(items[position], position)
                removeItem(items[position])
            }
        }


    }

    override fun getItemCount(): Int = items.size

    fun nextId(): Int? = items[items.size - 1].id?.plus(1)

    private fun removeItem(room: RoomModel) {
        items.remove(room)
        notifyDataSetChanged()
    }

    fun setItems(items: ArrayList<RoomModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(room: RoomModel, position: Int) {
        items.add(position, room)
        notifyDataSetChanged()
    }

}
