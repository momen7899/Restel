package com.momen.restel.room.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.momen.restel.R
import com.momen.restel.Utils
import com.momen.restel.comm.Toasty
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

            items[position].id?.let {
                if (Utils.isRoomInReserves(it)) {
                    remove.setImageResource(R.drawable.ic_disable_remove)
                    remove.setOnClickListener {
                        fragment.context?.let { it1 ->
                            Toasty.showWarningToasty(
                                it1,
                                it1.getString(R.string.disableDelete)
                            )
                        }
                    }
                } else {
                    remove.setImageResource(R.drawable.ic_remove)
                    remove.setOnClickListener {
                        fragment.showDelMsg(items[position], position)
                        removeItem(items[position])
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun nextId(): Int? {
        return if (items.isEmpty())
            0
        else items[items.size - 1].id?.plus(1)
    }

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

    fun filterItems(items: ArrayList<RoomModel>, search: String) {
        val list = ArrayList<RoomModel>()
        items.forEach {
            if (it.name.contains(search) || it.price.contains(search) || it.capacity.contains(search))
                list.add(it)
        }
        setItems(list)
    }
}