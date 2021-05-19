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
import com.momen.restel.room.model.RoomModel
import kotlinx.android.synthetic.main.fragment_room.*

class RoomFragment : Fragment() {

    private var update: Boolean = false
    private val roomAdapter = RoomAdapter()
    private var bottomSheetDialog: BottomSheetDialog? = null

    // bottom sheet components
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
        capacityEt = bottomSheetDialog?.findViewById(R.id.userFirstName)
        priceEt = bottomSheetDialog?.findViewById(R.id.userLastName)
        submit = bottomSheetDialog?.findViewById(R.id.submitBtn)
    }

    private fun setUpBottomSheetSubmit() {
        submit?.setOnClickListener {
            val room = validateData()
        }
    }

    private fun validateData(): RoomModel? {
        return null
    }

    private fun showBottomSheet(update: Boolean) {
        this.update = update
        bottomSheetDialog?.show()
    }

}