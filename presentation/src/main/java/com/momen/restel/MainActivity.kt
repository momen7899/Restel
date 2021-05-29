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
        setAppLocal()
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar as Toolbar)
        act = this
        setDirection()

        setupActionbarWithNavController()
    }

    private fun setAppLocal() {
        val language = Utils.getLanguage(this)
        Utils.setAppLocale(this, language)
    }

    private fun setDirection() {
        mainDrawer.layoutDirection =
            if (Utils.getRtl(this)) View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR
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