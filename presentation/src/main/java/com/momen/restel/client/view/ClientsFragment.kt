package com.momen.restel.client.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.addTextChangedListener
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.momen.restel.R
import com.momen.restel.Utils
import com.momen.restel.app.App
import com.momen.restel.app.RoomDbModule
import com.momen.restel.client.di.DaggerClientComponent
import com.momen.restel.client.viewmodel.ClientViewModel
import com.momen.restel.client.viewmodel.ClientViewModelFactory
import com.momen.restel.comm.Toasty
import com.momen.restel.login.model.UserModel
import kotlinx.android.synthetic.main.card_delete.*
import kotlinx.android.synthetic.main.fragment_client.*
import kotlinx.android.synthetic.main.toolbar_sample.view.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class ClientsFragment : Fragment() {

    @Inject
    lateinit var clientViewModelFactory: ClientViewModelFactory

    private var clientViewModel: ClientViewModel? = null
    private var clientAdapter: ClientAdapter? = null
    private var bottomSheetDialog: BottomSheetDialog? = null
    private var update = false
    private var id: Int? = null
    private val users = ArrayList<UserModel>()

    // bottom sheet components
    private var userFirstName: EditText? = null
    private var userLastName: EditText? = null
    private var userNationalCode: EditText? = null
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
        subscribeGetUsers()
        subscribeGetUser()
        subscribeAddUser()
        subscribeEditUser()
        subscribeRemoveUser()
    }

    private fun subscribeGetUsers() {
        clientViewModel?.getUsers()

        clientViewModel?.getUsersLiveData?.observe(viewLifecycleOwner, { result ->
            when (result.state) {
                ClientViewModel.State.LOADING_DATA -> {

                }
                ClientViewModel.State.DATA_LOADED -> {
                    result.users?.let {
                        clientAdapter?.setItems(it)
                        users.clear()
                        users.addAll(it)
                    }

                }

                ClientViewModel.State.LOAD_ERROR -> {

                }
            }
        })
    }

    private fun subscribeGetUser() {
        clientViewModel?.getUserLiveData?.observe(viewLifecycleOwner, { result ->
            when (result.state) {
                ClientViewModel.State.LOADING_DATA -> {

                }
                ClientViewModel.State.DATA_LOADED -> {
                    result.response?.let {
                        if (it > 0) {
                            clientViewModel?.getUsers()
                        }
                    }
                }

                ClientViewModel.State.LOAD_ERROR -> {

                }
            }
        })
    }

    private fun subscribeAddUser() {

        clientViewModel?.addUserLiveData?.observe(viewLifecycleOwner, { result ->
            when (result.state) {
                ClientViewModel.State.LOADING_DATA -> {

                }
                ClientViewModel.State.DATA_LOADED -> {
                    println(result.response)
                    result.response?.let {
                        if (it > 0) {
                            clientViewModel?.getUsers()
                        }
                    }
                }

                ClientViewModel.State.LOAD_ERROR -> {
                    println(result.error)
                }
            }
        })

    }

    private fun subscribeEditUser() {
        clientViewModel?.editUserLiveData?.observe(viewLifecycleOwner, { result ->
            when (result.state) {
                ClientViewModel.State.LOADING_DATA -> {

                }
                ClientViewModel.State.DATA_LOADED -> {
                    result.response?.let {
                        if (it > 0) {
                            clientViewModel?.getUsers()
                        }
                    }
                }

                ClientViewModel.State.LOAD_ERROR -> {

                }
            }
        })
    }

    private fun subscribeRemoveUser() {
        clientViewModel?.removeUserLiveData?.observe(viewLifecycleOwner, { result ->
            when (result.state) {
                ClientViewModel.State.LOADING_DATA -> {

                }
                ClientViewModel.State.DATA_LOADED -> {

                }

                ClientViewModel.State.LOAD_ERROR -> {

                }
            }
        })
    }

    private fun setUpComponents() {
        setUpToolbar()
        setUpFab()
        setUpUpRecycler()
        setUpBottomSheet()
        setUpBottomSheetComponent()
        setUpBottomSheetSubmit()
        hideActivityComponent()
    }

    private fun setUpToolbar() {
        clientToolbar.back.setOnClickListener {
            requireActivity().onBackPressed()
        }
        (clientToolbar.search as EditText).addTextChangedListener {
            clientAdapter?.filterItems(
                users, (clientToolbar.search as EditText).text.toString().trim()
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
        clientFab.setOnClickListener {
            showBottomSheet(false, null)
        }
    }

    private fun setUpBottomSheet() {
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog?.setContentView(R.layout.bottom_sheet_client)
        val rtl = true
        val layout = bottomSheetDialog?.findViewById<ScrollView>(R.id.bottomSheetClient)
        layout?.layoutDirection = if (rtl) View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR
    }

    private fun setUpBottomSheetComponent() {
        userFirstName = bottomSheetDialog?.findViewById(R.id.userFirstName)
        userLastName = bottomSheetDialog?.findViewById(R.id.userLastName)
        userNationalCode = bottomSheetDialog?.findViewById(R.id.userNationalCode)
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

            if (update) user?.let { clientViewModel?.editUser(it) }
            else user?.let { clientViewModel?.addUser(it) }
            bottomSheetDialog?.dismiss()
        }
    }

    private fun validateData(): UserModel? {
        val firstName = userFirstName?.text.toString().trim()
        val lastName = userLastName?.text.toString().trim()
        val nationalCode = userNationalCode?.text.toString().trim()
        val phone = userPhoneNumber?.text.toString().trim()
        val name = userName?.text.toString().trim()
        val pass = userPass?.text.toString().trim()
        val rePass = userRePass?.text.toString().trim()
        val address = userAddress?.text.toString().trim()

        if (validateInput(firstName, userFirstName)) return null
        if (validateInput(lastName, userLastName)) return null
        if (validateInput(nationalCode, userNationalCode)) return null
        if (validateInput(phone, userPhoneNumber)) return null
        if (validateInput(name, userName)) return null
        if (validateInput(pass, userPass)) return null
        if (validateInput(rePass, userRePass)) return null
        if (validateInput(address, userAddress)) return null
        if (validPassword(pass, rePass)) {
            return null
        }

        println(clientAdapter?.nextId())
        return if (update) UserModel(
            id, firstName, lastName, nationalCode,
            phone, name, pass, Utils.md5(pass), address, 0
        )
        else
            UserModel(
                clientAdapter?.nextId(), firstName, lastName, nationalCode,
                phone, name, pass, Utils.md5(pass), address, 0
            )
    }

    private fun validPassword(pass: String, rePass: String): Boolean = pass != rePass

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

    fun showBottomSheet(update: Boolean, user: UserModel?) {
        this.update = update
        id = user?.id
        setBottomSheetData(user)
        bottomSheetDialog?.show()
    }

    private fun setBottomSheetData(user: UserModel?) {
        if (update)
            user?.let { fillBottomSheetData(it) }
        else
            clearBottomSheetData()
    }

    private fun fillBottomSheetData(user: UserModel) {
        userFirstName?.setText(user.firstName)
        userLastName?.setText(user.lastName)
        userNationalCode?.setText(user.nationalCode)
        userPhoneNumber?.setText(user.phoneNumber)
        userName?.setText(user.userName)
        userPass?.setText(user.password)
        userRePass?.setText(user.password)
        userAddress?.setText(user.address)
        submit?.text = getString(R.string.edit)
    }

    private fun clearBottomSheetData() {
        userFirstName?.setText("")
        userLastName?.setText("")
        userNationalCode?.setText("")
        userPhoneNumber?.setText("")
        userName?.setText("")
        userPass?.setText("")
        userRePass?.setText("")
        userAddress?.setText("")
        submit?.text = getString(R.string.submit)
    }

    private fun setUpUpRecycler() {
        clientAdapter = ClientAdapter(this)
        clientRecycle.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        clientRecycle.adapter = clientAdapter
    }

    fun showDelMsg(user: UserModel, position: Int) {
        showDelete()
        setUpDelete(user, position)
    }

    private fun setUpDelete(user: UserModel, position: Int) {
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
                    if (delete) {
                        clientViewModel?.removeUser(user)
                        delete = false
                    }
                }
            }
        }, 0, 50)

        undoRemove.setOnClickListener {
            delete = false
            clientAdapter?.addItem(user, position)
            hideDelete()
        }
        timerProgressBar?.progress = sec
        timerCounter?.text = sec.toString()
    }

    private fun showDelete() {
        clientDelete.visibility = View.VISIBLE
        clientFab.visibility = View.GONE
    }

    private fun hideDelete() {
        clientDelete.visibility = View.GONE
        clientFab.visibility = View.VISIBLE
    }
}