package com.momen.restel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.momen.restel.login.view.LoginFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadLoginFragment()
    }

    private fun loadLoginFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frag, LoginFragment())
            .commitNow()
    }

}