package com.momen.restel

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar as Toolbar)
        act = this
        setupActionbarWithNavController()
        setDirection()
    }

    private fun setDirection() {
        if (getString(R.string.welcome) == "خوش آمدید")
            Utils.setRtl(true)
        else if (getString(R.string.welcome) == "Welcome") {
            Utils.setRtl(false)
        }
        mainDrawer.layoutDirection =
            if (Utils.getRtl()) View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR

    }

    private fun setupActionbarWithNavController() {
        val navController = findNavController(R.id.navHostFragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean =
        findNavController(R.id.navHostFragment).navigateUp(appBarConfiguration) ||
                super.onSupportNavigateUp()


    companion object {
        private lateinit var act: MainActivity
        fun instance(): MainActivity = act
    }
}