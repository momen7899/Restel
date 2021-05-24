package com.momen.restel.customer.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.momen.restel.R
import com.momen.restel.customer.model.CustomerModel
import com.momen.restel.databinding.CustomerItemBinding

class CustomerAdapter(private val fragment: CustomerFragment) :
    RecyclerView.Adapter<CustomerAdapter.PassengerViewHolder>() {

    private val items = ArrayList<CustomerModel>()

    class PassengerViewHolder(val passenger: CustomerItemBinding) :
        RecyclerView.ViewHolder(passenger.root) {
        val edit: ImageView = passenger.root.findViewById(R.id.customerItemEdit)
        val remove: ImageView = passenger.root.findViewById(R.id.customerItemRemove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassengerViewHolder =
        PassengerViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.customer_item, parent, false
            ) as CustomerItemBinding
        )

    override fun onBindViewHolder(holder: PassengerViewHolder, position: Int) {
        //  holder.passenger.customer = items[position]
        with(holder) {
            passenger.customer = items[position]
            edit.setOnClickListener {
                fragment.showBottomSheet(true, items[position])
            }

            remove.setOnClickListener {
                fragment.showDelMsg(items[position], position)
                removeItem(items[position])
            }
        }

    }

    private fun removeItem(customerModel: CustomerModel) {
        items.remove(customerModel)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    fun nextId(): Int? = items[items.size - 1].id?.plus(1)

    fun setItems(items: ArrayList<CustomerModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(customer: CustomerModel, position: Int) {
        items.add(position, customer)
        notifyDataSetChanged()
    }

}
