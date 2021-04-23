package com.momen.restel.client.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.momen.restel.R
import com.momen.restel.databinding.ClientItemBinding
import com.momen.restel.login.model.UserModel

class ClientAdapter : RecyclerView.Adapter<ClientAdapter.ClientViewHolder>() {

    private val items = ArrayList<UserModel>()

    class ClientViewHolder(val client: ClientItemBinding) : RecyclerView.ViewHolder(client.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder =
        ClientViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.client_item, parent, false
            ) as ClientItemBinding
        )

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        val currentUser: UserModel = items[position]
        holder.client.user = currentUser
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: ArrayList<UserModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}