package com.momen.restel.contact.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.momen.restel.R
import kotlinx.android.synthetic.main.fragment_contact.*

class ContactFragment : Fragment() {

    private val rtl = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_contact, container, false)

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
        fragmentContact.layoutDirection =
            if (rtl) View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR
    }

    private fun setBack() {
        contactBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setViews() {
        val intent = Intent(Intent.ACTION_VIEW)
        contactUsInstagram.setOnClickListener {
            intent.data = Uri.parse("https://instagram.com/momen7899?igshid=1vm2oepxazfg4")
            startActivity(intent)
        }
        contactUsInstagram.setOnClickListener {
            intent.data = Uri.parse("https://t.me/momen7899")
            startActivity(intent)
        }

        contactUsTweeter.setOnClickListener {
            intent.data = Uri.parse("https://twitter.com/Momen7899")
            startActivity(intent)
        }

        contactUsGmail.setOnClickListener {
            intent.data = Uri.parse("momenhamaveisi7@gmail.com")
            intent.setClassName(
                "com.google.android.gm",
                "com.google.android.gm.ComposeActivityGmail"
            )
            startActivity(intent)
        }

        contactUsCall.setOnClickListener {
            intent.data = Uri.parse("tel:09184394657")
            startActivity(intent)
        }

        contactUsSms.setOnClickListener {
            intent.data = Uri.parse("sms:09184394657")
            startActivity(intent)
        }
    }


}