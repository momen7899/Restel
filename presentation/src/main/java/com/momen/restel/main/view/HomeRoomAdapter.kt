package com.momen.restel.main.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.momen.restel.R
import com.momen.restel.main.model.HomeRoomModel

class HomeRoomAdapter(private val fragment: MainFragment) :
    RecyclerView.Adapter<HomeRoomAdapter.RoomViewHolder>() {

    private val items = ArrayList<HomeRoomModel>()

    class RoomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.mainCustomerName)
        val nationalCode: TextView = view.findViewById(R.id.mainCustomerNationalCode)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder =
        RoomViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.main_customer_item, parent, false)
        )

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
//        holder.title.text = items[position].name
//        holder.nationalCode.text = items[position].nationalCode
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: ArrayList<HomeRoomModel>) {
        this.items.clear()
        this.items.addAll(items)
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

