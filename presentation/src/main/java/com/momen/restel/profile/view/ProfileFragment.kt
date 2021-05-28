package com.momen.restel.profile.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.momen.restel.R
import com.momen.restel.Utils
import kotlinx.android.synthetic.main.card_scroll_profile.*
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUpComponents()
    }

    private fun setUpComponents() {
        hideActivityComponent()
        setDirection()
        setBack()
        setViews()
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


    private fun setDirection() {
        profileFragment.layoutDirection =
            if (Utils.getRtl()) View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR
    }

    private fun setBack() {
        profileBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setViews() {
        val user = Utils.getUser()

        profileFirstName.text = user?.firstName
        profileLastName.text = user?.lastName
        profileNationalCode.text = user?.nationalCode
        profilePhoneNumber.text = user?.phoneNumber
        profileUserName.text = user?.userName
        profileAddress.text = user?.address

    }

}