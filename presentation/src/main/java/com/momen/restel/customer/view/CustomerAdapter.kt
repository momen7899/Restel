package com.momen.restel.customer.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.momen.restel.R
import com.momen.restel.customer.model.CustomerModel
import com.momen.restel.databinding.CustomerItemBinding

class CustomerAdapter : RecyclerView.Adapter<CustomerAdapter.PassengerViewHolder>() {

    private val items = ArrayList<CustomerModel>()

    class PassengerViewHolder(val passenger: CustomerItemBinding) : RecyclerView.ViewHolder(passenger.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassengerViewHolder =
        PassengerViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.customer_item, parent, false
            ) as CustomerItemBinding
        )

    override fun onBindViewHolder(holder: PassengerViewHolder, position: Int) {
        holder.passenger.customer = items[position]
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: ArrayList<CustomerModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

}
