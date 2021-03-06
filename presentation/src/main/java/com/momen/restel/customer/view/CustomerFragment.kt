package com.momen.restel.customer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.ScrollView
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.addTextChangedListener
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.momen.restel.MainActivity
import com.momen.restel.R
import com.momen.restel.Utils
import com.momen.restel.app.App
import com.momen.restel.app.RoomDbModule
import com.momen.restel.comm.Toasty
import com.momen.restel.customer.di.DaggerCustomerComponent
import com.momen.restel.customer.model.CustomerModel
import com.momen.restel.customer.viewmodel.CustomerViewModel
import com.momen.restel.customer.viewmodel.CustomerViewModelFactory
import kotlinx.android.synthetic.main.card_delete.*
import kotlinx.android.synthetic.main.fragment_customer.*
import kotlinx.android.synthetic.main.toolbar_sample.view.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class CustomerFragment : Fragment() {

    @Inject
    lateinit var customerViewModelFactory: CustomerViewModelFactory

    private var customerAdapter: CustomerAdapter? = null
    private var customerViewModel: CustomerViewModel? = null
    private var update: Boolean = false
    private var bottomSheetDialog: BottomSheetDialog? = null
    private var id: Int? = null
    private val customers = ArrayList<CustomerModel>()

    // bottom sheet components
    private var customerNameEt: EditText? = null
    private var phoneNumberEt: EditText? = null
    private var customerNationalEt: EditText? = null
    private var maleRb: RadioButton? = null
    private var femaleRb: RadioButton? = null
    private var singleRb: RadioButton? = null
    private var marriedRb: RadioButton? = null
    private var submit: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_customer, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        injectViewModels()

        super.onActivityCreated(savedInstanceState)

        setUpViewModel()
        setUpComponents()
        subscribeToViewModels()
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

    private fun subscribeToViewModels() {
        subscribeGetCustomers()
        subscribeAddCustomer()
        subscribeEditCustomer()
        subscribeRemoveCustomer()
    }

    private fun subscribeGetCustomers() {
        customerViewModel?.getCustomers()

        customerViewModel?.getCustomerLiveData?.observe(viewLifecycleOwner, { result ->
            when (result.state) {
                CustomerViewModel.State.LOADING_DATA -> {

                }
                CustomerViewModel.State.DATA_LOADED -> {
                    result.customers?.let {
                        customerAdapter?.setItems(it)
                        customers.clear()
                        customers.addAll(it)
                    }
                }
                CustomerViewModel.State.LOAD_ERROR -> {

                }
            }
        })
    }

    private fun subscribeAddCustomer() {
        customerViewModel?.addCustomerLiveData?.observe(viewLifecycleOwner, { result ->
            when (result.state) {
                CustomerViewModel.State.LOADING_DATA -> {

                }
                CustomerViewModel.State.DATA_LOADED -> {
                    result.response?.let {
                        if (it >= 0) {
                            customerViewModel?.getCustomers()
                        }
                    }
                    Toasty.showSuccessToasty(
                        requireContext(),
                        getString(R.string.successDbTransaction)
                    )
                }
                CustomerViewModel.State.LOAD_ERROR -> {
                    Toasty.showErrorToasty(requireContext(), getString(R.string.DatabaseError))
                }
            }
        })
    }

    private fun subscribeEditCustomer() {
        customerViewModel?.editCustomerLiveData?.observe(viewLifecycleOwner, { result ->
            when (result.state) {
                CustomerViewModel.State.LOADING_DATA -> {

                }
                CustomerViewModel.State.DATA_LOADED -> {
                    result.response?.let {
                        if (it >= 0) {
                            customerViewModel?.getCustomers()
                        }
                        Toasty.showSuccessToasty(
                            requireContext(),
                            getString(R.string.successDbTransaction)
                        )
                    }
                }
                CustomerViewModel.State.LOAD_ERROR -> {
                    Toasty.showErrorToasty(requireContext(), getString(R.string.DatabaseError))
                }
            }
        })
    }

    private fun subscribeRemoveCustomer() {
        customerViewModel?.removeCustomerLiveData?.observe(viewLifecycleOwner, { result ->
            when (result.state) {
                CustomerViewModel.State.LOADING_DATA -> {
                    hideDelete()
                }
                CustomerViewModel.State.DATA_LOADED -> {
                    result.response?.let {
                        if (it >= 0) {
                            customerViewModel?.getCustomers()
                        }
                        Toasty.showSuccessToasty(
                            requireContext(),
                            getString(R.string.successDbTransaction)
                        )
                    }
                }
                CustomerViewModel.State.LOAD_ERROR -> {
                    Toasty.showErrorToasty(requireContext(), getString(R.string.DatabaseError))
                }
            }
        })
    }

    private fun setUpComponents() {
        hideActivityComponent()
        setUpToolbar()
        setUpFab()
        setUpRecycler()
        setUpBottomSheet()
        setUpBottomSheetComponent()
        setUpBottomSheetSubmit()
    }

    private fun setUpToolbar() {
        customerToolbar.back.setOnClickListener {
            requireActivity().onBackPressed()
        }
        (customerToolbar.search as EditText).addTextChangedListener {
            customerAdapter?.filterItems(
                customers, (customerToolbar.search as EditText).text.toString().trim()
            )
        }
    }

    private fun hideActivityComponent() {
        hideNavMenu()
        hideToolbar()
    }

    private fun hideNavMenu() {
        requireActivity().findViewById<DrawerLayout>(R.id.mainDrawer)
            .setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    private fun hideToolbar() {
        requireActivity().findViewById<Toolbar>(R.id.toolbar).visibility = View.GONE
    }


    private fun setUpFab() {
        customerFab.setOnClickListener {
            showBottomSheet(false, null)
        }
    }

    private fun setUpRecycler() {
        customerAdapter = CustomerAdapter(this)
        customerRecycle.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        customerRecycle.adapter = customerAdapter
    }

    private fun setUpBottomSheet() {
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog?.setContentView(R.layout.bottom_sheet_customer)
        val layout = bottomSheetDialog?.findViewById<ScrollView>(R.id.bottomSheetCustomer)
        layout?.layoutDirection =
            if (Utils.getRtl(MainActivity.instance())) View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR
    }

    private fun setUpBottomSheetComponent() {
        customerNameEt = bottomSheetDialog?.findViewById(R.id.customerNameEt)
        phoneNumberEt = bottomSheetDialog?.findViewById(R.id.phoneNumberEt)
        customerNationalEt = bottomSheetDialog?.findViewById(R.id.customerNationalEt)
        maleRb = bottomSheetDialog?.findViewById(R.id.customerMale)
        femaleRb = bottomSheetDialog?.findViewById(R.id.customerFemale)
        singleRb = bottomSheetDialog?.findViewById(R.id.customerSingle)
        marriedRb = bottomSheetDialog?.findViewById(R.id.customerMarried)
        submit = bottomSheetDialog?.findViewById(R.id.submitBtn)
    }

    private var customerMarried: String = ""
    private var customerGender: String = ""

    private fun setUpBottomSheetSubmit() {
        submit?.setOnClickListener {
            val customer = validateData()
            if (update) {
                customer?.let { it1 -> customerViewModel?.editCustomer(it1) }
            } else {
                customer?.let { it1 -> customerViewModel?.addCustomer(it1) }
            }
            bottomSheetDialog?.dismiss()
        }

        singleRb?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                customerMarried = getString(R.string.single)
        }
        marriedRb?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                customerMarried = getString(R.string.marry)
        }

        maleRb?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                customerGender = getString(R.string.male)
        }
        femaleRb?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                customerGender = getString(R.string.female)
        }
    }

    private fun validateData(): CustomerModel? {
        val name = customerNameEt?.text.toString().trim()
        val phoneNumber = phoneNumberEt?.text.toString().trim()
        val nationalId = customerNationalEt?.text.toString().trim()

        if (validateInput(name, customerNameEt)) return null
        if (validateInput(phoneNumber, phoneNumberEt)) return null
        if (validateInput(nationalId, customerNationalEt)) return null
        if (validateInput(customerMarried, singleRb)) return null
        if (validateInput(customerGender, maleRb)) return null

        return if (update) CustomerModel(
            id, name, phoneNumber, nationalId, customerGender, customerMarried
        ) else CustomerModel(
            customerAdapter?.nextId(), name,
            phoneNumber, nationalId, customerGender, customerMarried
        )
    }

    private fun validateInput(str: String?, et: View?): Boolean {
        str?.let {
            if (str.isEmpty()) {
                Toasty.showErrorToasty(requireContext(), getString(R.string.emptyEditText))
                et?.requestFocus()
                return true
            }
        }
        return false
    }

    fun showBottomSheet(update: Boolean, customer: CustomerModel?) {
        this.update = update
        id = customer?.id
        setBottomSheetData(customer)
        bottomSheetDialog?.show()
    }

    private fun setBottomSheetData(customer: CustomerModel?) {
        if (update)
            customer?.let { fillBottomSheetData(it) }
        else
            clearBottomSheetData()
    }

    private fun fillBottomSheetData(customer: CustomerModel) {
        customerNameEt?.setText(customer.name)
        phoneNumberEt?.setText(customer.phoneNumber)
        customerNationalEt?.setText(customer.nationalCode)
        maleRb?.isChecked = customer.gender == getString(R.string.male)
        femaleRb?.isChecked = customer.gender == getString(R.string.female)
        singleRb?.isChecked = customer.married == getString(R.string.single)
        marriedRb?.isChecked = customer.married == getString(R.string.marry)
        submit?.text = getString(R.string.edit)
    }

    private fun clearBottomSheetData() {
        customerNameEt?.setText("")
        phoneNumberEt?.setText("")
        customerNationalEt?.setText("")
        maleRb?.isChecked = false
        femaleRb?.isChecked = false
        singleRb?.isChecked = false
        marriedRb?.isChecked = false
        submit?.text = getString(R.string.submit)
    }

    fun showDelMsg(customer: CustomerModel, position: Int) {
        showDelete()
        setUpDelete(customer, position)
    }

    private fun setUpDelete(customer: CustomerModel, position: Int) {
        var sec = 60
        val timer = Timer()
        var delete = true
        timer.schedule(object : TimerTask() {
            override fun run() {
                sec--
                timerCounter?.text = ((sec + 21) / 20).toString()
                timerProgressBar?.progress = sec
                if (sec == 0) {
                    this.cancel()
                    hideDelete()
                    if (delete) {
                        customerViewModel?.removeCustomer(customer)
                        delete = false
                    }
                }
            }
        }, 0, 50)

        undoRemove.setOnClickListener {
            delete = false
            customerAdapter?.addItem(customer, position)
            hideDelete()
        }
        timerProgressBar?.progress = sec
        timerCounter?.text = sec.toString()
    }

    private fun showDelete() {
        customerDelete.visibility = View.VISIBLE
        customerFab.visibility = View.GONE
    }

    private fun hideDelete() {
        customerDelete.visibility = View.GONE
        customerFab.visibility = View.VISIBLE
    }
}