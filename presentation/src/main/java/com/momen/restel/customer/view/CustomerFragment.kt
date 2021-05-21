package com.momen.restel.customer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.momen.restel.R
import com.momen.restel.app.App
import com.momen.restel.app.RoomDbModule
import com.momen.restel.customer.di.DaggerCustomerComponent
import com.momen.restel.customer.viewmodel.CustomerViewModel
import com.momen.restel.customer.viewmodel.CustomerViewModelFactory
import com.momen.restel.room.view.RoomAdapter
import com.momen.restel.room.viewmodel.RoomViewModel
import kotlinx.android.synthetic.main.fragment_customer.*
import javax.inject.Inject

class CustomerFragment : Fragment() {

    @Inject
    lateinit var customerViewModelFactory: CustomerViewModelFactory

    private val passengerAdapter : CustomerAdapter? = null
    private var customerViewModel: CustomerViewModel? = null
    private var update: Boolean = false
    private var bottomSheetDialog: BottomSheetDialog? = null
    // bottom sheet components
    private var customerNameEt: EditText? = null
    private var phoneNumberEt: EditText? = null
    private var genderEt: EditText? = null
    private var marriedEt: EditText? = null
    private var submit: Button? = null

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
            .roomDbModule(RoomDbModule(requireContext()))
            .build()
            .inject(this)
    }
    private fun setUpViewModel() {
        customerViewModel =
            ViewModelProvider(this, customerViewModelFactory).get(CustomerViewModel::class.java)
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