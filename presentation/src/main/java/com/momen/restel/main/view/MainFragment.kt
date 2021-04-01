@file:Suppress("DEPRECATION")

package com.momen.restel.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.momen.restel.MainActivity
import com.momen.restel.R
import com.momen.restel.app.App
import com.momen.restel.app.RoomModule
import com.momen.restel.comm.Toasty
import com.momen.restel.main.di.DaggerMainComponent
import com.momen.restel.main.viewmodel.MainReserveViewModel
import com.momen.restel.main.viewmodel.MainReserveViewModelFactory
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_reserve.*
import javax.inject.Inject

@Suppress("DEPRECATION")
class MainFragment : Fragment() {

    @Inject
    lateinit var reserveViewModelFactory: MainReserveViewModelFactory

    private lateinit var reserveViewModel: MainReserveViewModel

    private
    val reserveAdapter = ReserveAdapter()

    // activity component
    private lateinit var toolbar: Toolbar
    private lateinit var drawer: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        injectViewModel()

        super.onActivityCreated(savedInstanceState)

        setUpViewModel()

        setUpComponents()
    }

    private fun injectViewModel() {
        DaggerMainComponent.builder()
            .appComponent(App().appComponent)
            .roomModule(RoomModule(requireContext()))
            .build()
            .inject(this)
    }

    private fun setUpViewModel() {
        reserveViewModel = ViewModelProviders.of(this, reserveViewModelFactory)
            .get(MainReserveViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        setUpActivityComponent()
        subscribeViewModel()
    }

    private fun subscribeViewModel() {
        reserveViewModel.getReserves()
        reserveViewModel.reserveLiveData.observe(
            viewLifecycleOwner, { result ->
                when (result.state) {
                    MainReserveViewModel.State.DATA_LOADED -> {
                        println(result.reserves)
                        reserveAdapter.setItems(result.reserves!!)
                    }
                    MainReserveViewModel.State.LOADING_DATA -> {
                    }
                    MainReserveViewModel.State.LOAD_ERROR -> {
                        Toasty.showErrorToasty(
                            requireContext(), getString(R.string.DatabaseError)
                        )
                        println(result.error)
                    }
                }
            }
        )
//        reserveAdapter.setItems(list)
    }

    private fun setUpComponents() {
        setUpFab()
        reserveRecycleSetUp()
    }

    private fun setUpFab() {
        mainFab.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToReserveFragment()
            it.findNavController().navigate(action)
        }
    }

    private fun reserveRecycleSetUp() {
        reserveRecycle.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        reserveRecycle.adapter = reserveAdapter
    }

    private fun setUpActivityComponent() {
        initComponent()
        setUpToolbar()
        setUpDrawer()
        setUpNavView()
    }

    private fun initComponent() {
        toolbar = requireActivity().findViewById(R.id.toolbar)
        drawer = requireActivity().findViewById(R.id.mainDrawer)
        navigationView = requireActivity().findViewById(R.id.navView)
    }

    private fun setUpNavView() {
        navigationView.setNavigationItemSelectedListener {
            Toasty.showInfoToasty(requireContext(), getString(R.string.underConstruction))
            return@setNavigationItemSelectedListener true
        }
    }

    private fun setUpDrawer() {
        MainActivity.instance().supportActionBar?.setDisplayHomeAsUpEnabled(true)
        MainActivity.instance().supportActionBar?.setHomeButtonEnabled(true)

        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        val drawerToggle = ActionBarDrawerToggle(
            requireActivity(), drawer, toolbar, 0, 0
        )

        drawerToggle.isDrawerIndicatorEnabled = true
        drawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
    }

    private fun setUpToolbar() {
        toolbar.visibility = View.VISIBLE
        toolbar.title = ""
        val title: TextView = requireActivity().findViewById(R.id.toolbarTitle)
        title.text = "رزرو"
    }
}