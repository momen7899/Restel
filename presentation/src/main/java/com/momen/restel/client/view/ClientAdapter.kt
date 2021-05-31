package com.momen.restel.client.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.momen.restel.R
import com.momen.restel.Utils
import com.momen.restel.comm.Toasty
import com.momen.restel.databinding.ClientItemBinding
import com.momen.restel.login.model.UserModel

class ClientAdapter(private val fragment: ClientsFragment) :
    RecyclerView.Adapter<ClientAdapter.ClientViewHolder>() {

    private val items = ArrayList<UserModel>()

    class ClientViewHolder(val client: ClientItemBinding) : RecyclerView.ViewHolder(client.root) {
        val edit: ImageView = client.root.findViewById(R.id.clientItemEdit)
        val remove: ImageView = client.root.findViewById(R.id.clientItemRemove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder =
        ClientViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.client_item, parent, false
            ) as ClientItemBinding
        )

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        val currentUser: UserModel = items[position]

        with(holder) {
            client.user = currentUser

            edit.setOnClickListener {
                fragment.showBottomSheet(true, currentUser)
            }
            currentUser.id?.let {
                if (Utils.isClientInReserves(it)) {
                    remove.setImageResource(R.drawable.ic_disable_remove)
                    remove.setOnClickListener {
                        fragment.context?.let { it1 -> Toasty.showWarningToasty(it1,it1.getString(R.string.disableDelete)) }
                    }
                } else {
                    remove.setImageResource(R.drawable.ic_remove)
                    remove.setOnClickListener {
                        removeItem(currentUser)
                        fragment.showDelMsg(currentUser, position)
                    }
                }
            }
        }
    }

    private fun removeItem(user: UserModel) {
        items.remove(user)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    fun nextId(): Int? {
        return if (items.isEmpty()) 0
        else items[items.size - 1].id?.plus(1)
    }

    fun setItems(items: ArrayList<UserModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(user: UserModel, position: Int) {
        items.add(position, user)
        notifyDataSetChanged()
    }

    fun filterItems(items: ArrayList<UserModel>, search: String) {
        val list = ArrayList<UserModel>()
        items.forEach {
            if (it.firstName?.contains(search) == true || it.lastName?.contains(search) == true ||
                it.nationalCode?.contains(search) == true || it.phoneNumber?.contains(search) == true
            )
                list.add(it)
        }
        setItems(list)
    }
}