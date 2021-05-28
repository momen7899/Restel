package com.momen.restel.room.view

import android.os.Bundle
import android.util.Log
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
import com.momen.restel.app.App
import com.momen.restel.app.RoomDbModule
import com.momen.restel.comm.Toasty
import com.momen.restel.room.di.DaggerRoomComponent
import com.momen.restel.room.model.RoomModel
import com.momen.restel.room.viewmodel.RoomViewModel
import com.momen.restel.room.viewmodel.RoomViewModelFactory
import kotlinx.android.synthetic.main.card_delete.*
import kotlinx.android.synthetic.main.fragment_room.*
import kotlinx.android.synthetic.main.toolbar_sample.view.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class RoomFragment : Fragment() {

    @Inject
    lateinit var roomViewModelFactory: RoomViewModelFactory

    private var roomViewModel: RoomViewModel? = null
    private var update: Boolean = false
    private var id: Int? = null
    private var roomAdapter: RoomAdapter? = null
    private var bottomSheetDialog: BottomSheetDialog? = null
    private val rooms = ArrayList<RoomModel>()

    // bottom sheet components
    private var roomNameEt: EditText? = null
    private var capacityEt: EditText? = null
    private var priceEt: EditText? = null
    private var submit: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_room, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        injectViewModel()
        setUpViewModel()
        setUpComponents()
        subscribeViewModel()
    }

    private fun injectViewModel() {
        @Suppress("DEPRECATION")
        DaggerRoomComponent.builder()
            .appComponent(App().appComponent)
            .roomDbModule(RoomDbModule(requireContext()))
            .build()
            .inject(this)
    }

    private fun setUpViewModel() {
        roomViewModel =
            ViewModelProvider(this, roomViewModelFactory).get(RoomViewModel::class.java)
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

    private fun subscribeViewModel() {
        subscribeGetRooms()
        subscribeAddRoom()
        subscribeEditRoom()
        subscribeRemoveRoom()
    }

    private fun subscribeGetRooms() {
        roomViewModel?.getRooms()

        roomViewModel?.getRoomsLiveData?.observe(viewLifecycleOwner, { result ->
            when (result.state) {
                RoomViewModel.State.LOADING_DATA -> {

                }
                RoomViewModel.State.DATA_LOADED -> {
                    result.rooms?.let {
                        roomAdapter?.setItems(it)
                        rooms.clear()
                        rooms.addAll(it)
                    }
                }

                RoomViewModel.State.LOAD_ERROR -> {

                }
            }
        })
    }

    private fun subscribeAddRoom() {
        roomViewModel?.addRoomLiveData?.observe(viewLifecycleOwner, { result ->
            when (result.state) {
                RoomViewModel.State.LOADING_DATA -> {

                }
                RoomViewModel.State.DATA_LOADED -> {
                    result.response?.let {
                        if (it >= 0) {
                            roomViewModel?.getRooms()
                        }
                    }
                }
                RoomViewModel.State.LOAD_ERROR -> {
                    Log.i("AddRoom", result.error.toString())
                }
            }
        })
    }

    private fun subscribeEditRoom() {
        roomViewModel?.editRoomLiveData?.observe(viewLifecycleOwner, { result ->
            when (result.state) {
                RoomViewModel.State.LOADING_DATA -> {

                }
                RoomViewModel.State.DATA_LOADED -> {
                    result.response?.let {
                        if (it >= 0) {
                            roomViewModel?.getRooms()
                        }
                    }
                }

                RoomViewModel.State.LOAD_ERROR -> {

                }
            }
        })
    }

    private fun subscribeRemoveRoom() {
        roomViewModel?.removeRoomLiveData?.observe(viewLifecycleOwner, { result ->
            when (result.state) {
                RoomViewModel.State.LOADING_DATA -> {

                }
                RoomViewModel.State.DATA_LOADED -> {

                }

                RoomViewModel.State.LOAD_ERROR -> {

                }
            }
        })
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

    private fun setUpToolbar() {
        roomToolbar.back.setOnClickListener {
            requireActivity().onBackPressed()
        }
        (roomToolbar.search as EditText).addTextChangedListener {
            roomAdapter?.filterItems(
                rooms, (roomToolbar.search as EditText).text.toString().trim()
            )
        }
    }

    private fun setUpFab() {
        roomFab.setOnClickListener {
            showBottomSheet(false, null)
        }
    }

    private fun setUpRecycler() {
        roomAdapter = RoomAdapter(this)
        roomRecycle.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        roomRecycle.adapter = roomAdapter
    }

    private fun setUpBottomSheet() {
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog?.setContentView(R.layout.bottom_sheet_room)
        val rtl = true
        val layout = bottomSheetDialog?.findViewById<ScrollView>(R.id.bottomSheetRoom)
        layout?.layoutDirection = if (rtl) View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR
    }

    private fun setUpBottomSheetComponent() {
        roomNameEt = bottomSheetDialog?.findViewById(R.id.roomNameEt)
        capacityEt = bottomSheetDialog?.findViewById(R.id.capacityEt)
        priceEt = bottomSheetDialog?.findViewById(R.id.priceEt)
        submit = bottomSheetDialog?.findViewById(R.id.submitBtn)
    }

    private fun setUpBottomSheetSubmit() {
        submit?.setOnClickListener {
            val room = validateData()
            if (update) {
                room?.let { it1 -> roomViewModel?.editRoom(it1) }
            } else {
                room?.let { it1 -> roomViewModel?.addRoom(it1) }
            }
            bottomSheetDialog?.dismiss()
        }
    }

    private fun validateData(): RoomModel? {
        val name = roomNameEt?.text.toString().trim()
        val capacity = capacityEt?.text.toString().trim()
        val price = priceEt?.text.toString().trim()

        if (validateInput(name, roomNameEt)) return null
        if (validateInput(capacity, capacityEt)) return null
        if (validateInput(price, priceEt)) return null

        return if (update) RoomModel(id, name, capacity, price)
        else RoomModel(roomAdapter?.nextId(), name, capacity, price)
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

    fun showBottomSheet(update: Boolean, room: RoomModel?) {
        this.update = update
        id = room?.id
        setBottomSheetData(room)
        bottomSheetDialog?.show()
    }

    private fun setBottomSheetData(room: RoomModel?) {
        if (update)
            room?.let { fillBottomSheetData(it) }
        else
            clearBottomSheetData()
    }

    private fun fillBottomSheetData(room: RoomModel) {
        roomNameEt?.setText(room.name)
        capacityEt?.setText(room.capacity)
        priceEt?.setText(room.price)
        submit?.text = getString(R.string.edit)
    }

    private fun clearBottomSheetData() {
        roomNameEt?.setText("")
        capacityEt?.setText("")
        priceEt?.setText("")
        submit?.text = getString(R.string.submit)
    }

    fun showDelMsg(room: RoomModel, position: Int) {
        showDelete()
        setUpDelete(room, position)
    }

    private fun setUpDelete(room: RoomModel, position: Int) {
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
                        roomViewModel?.removeRoom(room)
                        delete = false
                    }
                }
            }
        }, 0, 50)

        undoRemove.setOnClickListener {
            delete = false
            roomAdapter?.addItem(room, position)
            hideDelete()
        }
        timerProgressBar?.progress = sec
        timerCounter?.text = sec.toString()
    }

    private fun showDelete() {
        roomDelete.visibility = View.VISIBLE
        roomFab.visibility = View.GONE
    }

    private fun hideDelete() {
        roomDelete.visibility = View.GONE
        roomFab.visibility = View.VISIBLE
    }
}