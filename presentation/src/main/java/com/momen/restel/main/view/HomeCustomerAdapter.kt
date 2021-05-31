package com.momen.restel.main.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.momen.restel.R
import com.momen.restel.main.model.HomeCustomerModel

class HomeCustomerAdapter(private val fragment: MainFragment) :
    RecyclerView.Adapter<HomeCustomerAdapter.CustomerViewHolder>() {

    private val items = ArrayList<HomeCustomerModel>()

    class CustomerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card: LinearLayout = view.findViewById(R.id.mainCustomer)
        val title: TextView = view.findViewById(R.id.mainCustomerName)
        val nationalCode: TextView = view.findViewById(R.id.mainCustomerNationalCode)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder =
        CustomerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.main_customer_item, parent, false)
        )

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        holder.card.setOnClickListener {
            fragment.setCustomerSelected(items[position])
        }
        holder.title.text = items[position].name
        holder.nationalCode.text = items[position].nationalCode
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: ArrayList<HomeCustomerModel>) {
        this.items.clear()
        this.items.addAll(items)
        if (items.isEmpty()){
            fragment.showEmptyCustomer()
        }else{
            fragment.hideEmptyCustomer()
        }
        notifyDataSetChanged()
    }

    fun filterItems(items: ArrayList<HomeCustomerModel>, search: String) {
        val list = ArrayList<HomeCustomerModel>()
        items.forEach {
            println(it.name?.contains(search))
            if (it.name?.contains(search) == true || it.nationalCode?.contains(search) == true)
                list.add(it)
        }
        setItems(list)
    }
}
