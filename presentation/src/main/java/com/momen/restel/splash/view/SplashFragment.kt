package com.momen.restel.splash.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.momen.restel.R
import com.momen.restel.app.App
import com.momen.restel.app.RoomDbModule
import com.momen.restel.splash.di.DaggerSplashComponent
import com.momen.restel.splash.viewmodel.SplashViewModel
import com.momen.restel.splash.viewmodel.SplashViewModelFactory
import javax.inject.Inject


class SplashFragment : Fragment() {

    @Inject
    lateinit var splashViewModelFactory: SplashViewModelFactory
    private lateinit var splashViewModel: SplashViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_splash, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        hideActivityComponent()
        injectViewModel()
        setupViewModel()
        splashViewModel.createUser()
    }

    override fun onResume() {
        super.onResume()
        loadLogin()
    }

    private fun loadLogin() {
        val navController = requireView().findNavController()
        val action = SplashFragmentDirections.actionSplashFragmentToLoginFragment()

        Handler(Looper.getMainLooper()).postDelayed({
            if (navController.currentDestination?.id == R.id.splashFragment) {
                navController.navigate(action)
            }
        }, 2000)
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

    private fun injectViewModel() {
        DaggerSplashComponent.builder()
            .appComponent((App()).appComponent)
            .roomDbModule(RoomDbModule(requireContext()))
            .build()
            .inject(this)
    }

    private fun setupViewModel() {
        splashViewModel = ViewModelProvider(this, splashViewModelFactory)
            .get(SplashViewModel::class.java)
    }

}