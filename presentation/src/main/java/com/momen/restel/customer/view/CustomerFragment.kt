package com.momen.restel.customer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.momen.restel.R
import com.momen.restel.app.App
import com.momen.restel.app.RoomDbModule
import com.momen.restel.customer.di.DaggerCustomerComponent
import com.momen.restel.customer.viewmodel.CustomerViewModelFactory
import kotlinx.android.synthetic.main.fragment_customer.*
import javax.inject.Inject

class CustomerFragment : Fragment() {

    @Inject
    lateinit var customerViewModelFactory: CustomerViewModelFactory

    private val passengerAdapter = CustomerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_customer, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        injectViewModels()

        super.onActivityCreated(savedInstanceState)

        setUpComponents()
    }

    private fun injectViewModels() {
        DaggerCustomerComponent.builder()
            .appComponent(App().appComponent)
            .roomModule(RoomDbModule(requireContext()))
            .build()
            .inject(this)
    }

    private fun setUpComponents() {
        setUpRecycler()

    }

    private fun setUpRecycler() {
        customerRecycle.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        customerRecycle.adapter = passengerAdapter
    }
}