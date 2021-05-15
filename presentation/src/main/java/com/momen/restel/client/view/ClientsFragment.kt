package com.momen.restel.client.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.momen.restel.R
import com.momen.restel.app.App
import com.momen.restel.app.RoomDbModule
import com.momen.restel.client.di.DaggerClientComponent
import com.momen.restel.client.viewmodel.ClientViewModel
import com.momen.restel.client.viewmodel.ClientViewModelFactory
import com.momen.restel.comm.Toasty
import com.momen.restel.login.model.UserModel
import kotlinx.android.synthetic.main.fragment_client.*
import javax.inject.Inject

class ClientsFragment : Fragment() {

    @Inject
    lateinit var clientViewModelFactory: ClientViewModelFactory

    private var clientViewModel: ClientViewModel? = null
    private val clientAdapter = ClientAdapter()
    private var bottomSheetDialog: BottomSheetDialog? = null
    private var update = false

    // bottom sheet components
    private var userFirstName: EditText? = null
    private var userLastName: EditText? = null
    private var userPhoneNumber: EditText? = null
    private var userName: EditText? = null
    private var userPass: EditText? = null
    private var userRePass: EditText? = null
    private var userAddress: EditText? = null
    private var submit: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_client, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        injectViewModel()

        super.onActivityCreated(savedInstanceState)

        setUpViewModel()

        setUpComponents()
        subscribeViewModel()
    }

    private fun injectViewModel() {
        DaggerClientComponent.builder()
            .appComponent(App().appComponent)
            .roomDbModule(RoomDbModule(requireContext()))
            .build()
            .inject(this)
    }

    private fun setUpViewModel() {
        clientViewModel =
            ViewModelProvider(this, clientViewModelFactory).get(ClientViewModel::class.java)
    }

    private fun subscribeViewModel() {
    }

    private fun setUpComponents() {
        setUpFab()
        setUpUpRecycler()
        setUpBottomSheet()
        setUpBottomSheetComponent()
        setUpBottomSheetSubmit()
    }

    private fun setUpFab() {
        clientFab.setOnClickListener {
            showBottomSheet(false)
        }
    }

    @SuppressLint("WrongConstant")
    private fun setUpBottomSheet() {
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog?.setContentView(R.layout.bottom_sheet_client)
        val rtl = true
        val layout =
            bottomSheetDialog?.findViewById<ScrollView>(R.id.bottomSheetClient)
        layout?.layoutDirection = if (rtl) 1 else 0
    }

    private fun setUpBottomSheetComponent() {
        userFirstName = bottomSheetDialog?.findViewById(R.id.userFirstName)
        userLastName = bottomSheetDialog?.findViewById(R.id.userLastName)
        userPhoneNumber = bottomSheetDialog?.findViewById(R.id.userPhoneNumber)
        userName = bottomSheetDialog?.findViewById(R.id.userName)
        userPass = bottomSheetDialog?.findViewById(R.id.userPass)
        userRePass = bottomSheetDialog?.findViewById(R.id.userRePass)
        userAddress = bottomSheetDialog?.findViewById(R.id.userAddress)
        submit = bottomSheetDialog?.findViewById(R.id.submitBtn)
    }

    private fun setUpBottomSheetSubmit() {
        submit?.setOnClickListener {
            val user = validateData()
            println(user)
        }
    }

    private fun validateData(): UserModel? {
        val firstName = userFirstName?.text.toString().trim()
        val lastName = userLastName?.text.toString().trim()
        val phone = userPhoneNumber?.text.toString().trim()
        val name = userName?.text.toString().trim()
        val pass = userPass?.text.toString().trim()
        val rePass = userRePass?.text.toString().trim()
        val address = userAddress?.text.toString().trim()

        if (validateInput(firstName, userFirstName)) return null
        if (validateInput(lastName, userLastName)) return null
        if (validateInput(phone, userPhoneNumber)) return null
        if (validateInput(name, userName)) return null
        if (validateInput(pass, userPass)) return null
        if (validateInput(rePass, userRePass)) return null
        if (validateInput(address, userAddress)) return null

        return UserModel(0, firstName, lastName, phone, name, pass, rePass, pass, address, 0)
    }

    private fun validateInput(str: String?, et: EditText?): Boolean {
        str?.let {
            if (str.isEmpty()) {
                Toasty.showErrorToasty(requireContext(), getString(R.string.roomNumberError))
                et?.requestFocus()
                return true
            }
        }
        return false
    }

    private fun showBottomSheet(update: Boolean) {
        this.update = update
        bottomSheetDialog?.show()
    }

    private fun setUpUpRecycler() {
        clientRecycle.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        clientRecycle.adapter = clientAdapter
    }

}