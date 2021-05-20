package com.momen.restel.room.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.momen.restel.R
import com.momen.restel.comm.Toasty
import com.momen.restel.room.model.RoomModel
import kotlinx.android.synthetic.main.fragment_room.*

class RoomFragment : Fragment() {

    private var update: Boolean = false
    private val roomAdapter = RoomAdapter()
    private var bottomSheetDialog: BottomSheetDialog? = null

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

        setUpComponents()
    }

    private fun setUpComponents() {
        setUpFab()
        setUpRecycler()
        setUpBottomSheet()
        setUpBottomSheetComponent()
        setUpBottomSheetSubmit()
    }

    private fun setUpFab() {
        roomFab.setOnClickListener {
            showBottomSheet(false)
        }
    }

    private fun setUpRecycler() {
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

        }
    }

    private fun validateData(): RoomModel? {
        val name = roomNameEt?.text.toString().trim()
        val capacity = capacityEt?.text.toString().trim()
        val price = priceEt?.text.toString().trim()

        if (validateInput(name, roomNameEt)) return null
        if (validateInput(capacity, capacityEt)) return null
        if (validateInput(price, priceEt)) return null

        return RoomModel(0, name, capacity, price)
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

}