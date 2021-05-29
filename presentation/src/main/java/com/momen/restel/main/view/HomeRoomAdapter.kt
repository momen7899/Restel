package com.momen.restel.main.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.momen.restel.R
import com.momen.restel.main.model.HomeRoomModel

class HomeRoomAdapter(private val fragment: MainFragment) :
    RecyclerView.Adapter<HomeRoomAdapter.RoomViewHolder>() {

    private val items = ArrayList<HomeRoomModel>()

    class RoomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card: LinearLayout = view.findViewById(R.id.mainRoom)
        val title: TextView = view.findViewById(R.id.mainRoomName)
        val capacity: TextView = view.findViewById(R.id.mainRoomCapacity)
        val price: TextView = view.findViewById(R.id.mainRoomPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder =
        RoomViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.main_room_item, parent, false)
        )

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        with(holder) {
            card.setOnClickListener {
                fragment.setRoomSelected(items[position])
            }
            title.text = fragment.getString(R.string.roomNumber).plus(items[position].name)
            capacity.text = fragment.getString(R.string.capacity).plus(items[position].capacity)
            price.text = fragment.getString(R.string.price).plus(items[position].price)
        }
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: ArrayList<HomeRoomModel>) {
        this.items.clear()
        this.items.addAll(items)
        if (items.isEmpty()){
            fragment.showEmptyRoom()
        }else{
            fragment.hideEmptyRoom()
        }
        notifyDataSetChanged()
    }

    fun filterItems(items: ArrayList<HomeRoomModel>, search: String) {
        val list = ArrayList<HomeRoomModel>()
        items.forEach {
            if (it.name.contains(search) || it.price.contains(search) || it.capacity.contains(search))
                list.add(it)
        }
        setItems(list)
    }
}

