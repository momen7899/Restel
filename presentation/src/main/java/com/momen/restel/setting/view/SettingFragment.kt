package com.momen.restel.setting.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.momen.restel.MainActivity
import com.momen.restel.R
import com.momen.restel.Utils
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_setting, container, false)

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
        settingFragment.layoutDirection =
            if (Utils.getRtl(MainActivity.instance())) View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR
    }

    private fun setBack() {
        settingBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setViews() {
        lanPersian.setOnClickListener {
            Utils.setLanguage("fa", MainActivity.instance())
            Utils.setRtl(true, MainActivity.instance())
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }
        lanArabic.setOnClickListener {
            Utils.setLanguage("ar", MainActivity.instance())
            Utils.setRtl(true, MainActivity.instance())
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }
        lanEng.setOnClickListener {
            Utils.setLanguage("en", MainActivity.instance())
            Utils.setRtl(false, MainActivity.instance())
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }
        lanSpain.setOnClickListener {
            Utils.setLanguage("es", MainActivity.instance())
            Utils.setRtl(false, MainActivity.instance())
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }
        lanJapan.setOnClickListener {
            Utils.setLanguage("ja", MainActivity.instance())
            Utils.setRtl(false, MainActivity.instance())
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }
        lanFrance.setOnClickListener {
            Utils.setLanguage("fr", MainActivity.instance())
            Utils.setRtl(false, MainActivity.instance())
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }
    }

}