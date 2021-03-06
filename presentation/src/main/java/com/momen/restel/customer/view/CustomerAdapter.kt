package com.momen.restel.customer.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.momen.restel.R
import com.momen.restel.Utils
import com.momen.restel.comm.Toasty
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

            items[position].id?.let {
                if (Utils.isCustomerInReserves(it)) {
                    remove.setImageResource(R.drawable.ic_disable_remove)
                    remove.setOnClickListener {
                        fragment.context?.let { it1 -> Toasty.showWarningToasty(it1,it1.getString(R.string.disableDelete)) }
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

    private fun removeItem(customerModel: CustomerModel) {
        items.remove(customerModel)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    fun nextId(): Int? {
        return if (items.isEmpty()) 0
        else items[items.size - 1].id?.plus(1)
    }

    fun setItems(items: ArrayList<CustomerModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(customer: CustomerModel, position: Int) {
        items.add(position, customer)
        notifyDataSetChanged()
    }

    fun filterItems(items: ArrayList<CustomerModel>, search: String) {
        val list = ArrayList<CustomerModel>()
        items.forEach {
            if (it.name?.contains(search) == true || it.nationalCode?.contains(search) == true ||
                it.phoneNumber?.contains(search) == true
            )
                list.add(it)
        }
        setItems(list)
    }

}
