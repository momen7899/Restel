@file:Suppress("DEPRECATION")

package com.momen.restel.reserve.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.navigation.NavigationView
import com.momen.restel.R
import com.momen.restel.app.App
import com.momen.restel.app.RoomModule
import com.momen.restel.comm.Toasty
import com.momen.restel.reserve.di.DaggerReserveComponent
import com.momen.restel.reserve.model.ReserveModel
import com.momen.restel.reserve.viewmodel.ReserveViewModel
import com.momen.restel.reserve.viewmodel.ReserveViewModelFactory
import kotlinx.android.synthetic.main.fragment_reserve.*
import javax.inject.Inject

@Suppress("DEPRECATION")
class ReserveFragment : Fragment() {

    @Inject
    lateinit var reserveViewModelFactory: ReserveViewModelFactory

    private lateinit var reserveViewModel: ReserveViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_reserve, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        injectViewModel()

        super.onActivityCreated(savedInstanceState)

        setUpViewModel()
        setUpComponents()

    }

    override fun onResume() {
        super.onResume()
        hideActivityComponent()
    }

    private fun hideActivityComponent() {
        hideNavMenu()
        hideToolbar()
    }

    private fun hideNavMenu() {
        requireActivity().findViewById<DrawerLayout>(R.id.mainDrawer)
            .setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        requireActivity().findViewById<NavigationView>(R.id.navView).visibility = View.GONE
    }

    private fun hideToolbar() {
        requireActivity().findViewById<Toolbar>(R.id.toolbar).visibility = View.GONE
    }

    private fun injectViewModel() {
        DaggerReserveComponent.builder()
            .appComponent(App().appComponent)
            .roomModule(RoomModule(requireContext()))
            .build()
            .inject(this)
    }

    private fun setUpViewModel() {
        reserveViewModel = ViewModelProviders.of(this, reserveViewModelFactory)
            .get(ReserveViewModel::class.java)
    }

    private fun setUpComponents() {
        setHideKeyboardForm()
        setUpSubmitBtn()
    }

    private fun setHideKeyboardForm() {
        reserveFragment.setOnClickListener { hideKeyboardFrom() }
        reserveLayout.setOnClickListener { hideKeyboardFrom() }
    }

    private fun setUpSubmitBtn() {
        submitBtn.setOnFocusChangeListener { _, _ ->
            hideKeyboardFrom()
        }
        submitBtn.setOnClickListener {
            hideKeyboardFrom()
            val room = reserveRoom.text.toString().trim()
            val customer = reserveCustomer.text.toString().trim()
            val first = reserveFirstDate.text.toString().trim()
            val end = reserveLastDate.text.toString().trim()
            val priceRoom = reservePrice.text.toString().trim().toInt()

            if (validateInput(room, reserveRoom)) return@setOnClickListener
            if (validateInput(customer, reserveCustomer)) return@setOnClickListener
            if (validateInput(first, reserveFirstDate)) return@setOnClickListener
            if (validateInput(end, reserveLastDate)) return@setOnClickListener
            if (validateInput(priceRoom.toString(), reservePrice)) return@setOnClickListener

            subscribeViewModel(
                ReserveModel(0, room, "مؤمن حمه ویسی", customer, first, end, priceRoom)
            )

        }
    }

    private fun subscribeViewModel(reserveModel: ReserveModel) {
        reserveViewModel.addReserve(reserveModel)
        reserveViewModel.reserveLiveData.observe(
            viewLifecycleOwner, { result ->
                when (result.state) {
                    ReserveViewModel.State.DATA_LOADED -> {
                        submitBtn.isEnabled = true
                        if (result.response!! > 0) {
                            Toasty.showSuccessToasty(
                                requireContext(), getString(R.string.successTransaction)
                            )
                        } else {
                            Toasty.showErrorToasty(
                                requireContext(), getString(R.string.unsuccessTransaction)
                            )
                        }
                    }
                    ReserveViewModel.State.LOADING_DATA -> {
                        submitBtn.isEnabled = false
                    }
                    ReserveViewModel.State.LOAD_ERROR -> {
                        submitBtn.isEnabled = true
                        Toasty.showErrorToasty(
                            requireContext(), getString(R.string.DatabaseError)
                        )
                        println(result.error)
                    }
                }
            }
        )

    }

    private fun validateInput(room: String, et: EditText): Boolean {
        if (room.isEmpty()) {
            Toasty.showErrorToasty(requireContext(), getString(R.string.roomNumberError))
            et.requestFocus()
            return true
        }
        return false
    }

    private fun hideKeyboardFrom() {
        val imm: InputMethodManager =
            requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

}