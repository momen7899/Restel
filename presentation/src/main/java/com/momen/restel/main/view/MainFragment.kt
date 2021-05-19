package com.momen.restel.main.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aminography.primecalendar.persian.PersianCalendar
import com.aminography.primedatepicker.picker.PrimeDatePicker
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import com.momen.restel.MainActivity
import com.momen.restel.R
import com.momen.restel.app.App
import com.momen.restel.app.RoomDbModule
import com.momen.restel.comm.Toasty
import com.momen.restel.main.di.DaggerMainComponent
import com.momen.restel.main.viewmodel.ReserveViewModel
import com.momen.restel.main.viewmodel.ReserveViewModelFactory
import com.momen.restel.reserve.model.ReserveModel
import kotlinx.android.synthetic.main.bottom_sheet_reserve.*
import kotlinx.android.synthetic.main.bottom_sheet_reserve.view.*
import kotlinx.android.synthetic.main.card_input.view.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_reserve.*
import java.util.*
import javax.inject.Inject

class MainFragment : Fragment() {

    private val rtl = true

    @Inject
    lateinit var reserveViewModelFactory: ReserveViewModelFactory

    private lateinit var reserveViewModel: ReserveViewModel

    private val reserveAdapter = ReserveAdapter()
    private var bottomSheetDialog: BottomSheetDialog? = null
    private var update = false
    private var date: TextView? = null
    private var submit: Button? = null
    private var reserveRoom: EditText? = null
    private var reserveCustomer: EditText? = null
    private var reservePrice: EditText? = null

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
        subscribeViewModel()
    }

    private fun injectViewModel() {
        DaggerMainComponent.builder()
            .appComponent(App().appComponent)
            .roomDbModule(RoomDbModule(requireContext()))
            .build()
            .inject(this)
    }

    private fun setUpViewModel() {
        reserveViewModel = ViewModelProvider(this, reserveViewModelFactory)
            .get(ReserveViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        setUpActivityComponent()
        getReserves()
    }

    private fun getReserves() {
        reserveViewModel.getReserves()
    }

    private fun subscribeViewModel() {
        subscribeGetReserves()
        subscribeAddReserve()
        subscribeUpdateReserve()
        subscribeRemoveReserve()
    }

    private fun subscribeGetReserves() {
        reserveViewModel.reserveLiveData.observe(
            viewLifecycleOwner, { result ->
                when (result.state) {
                    ReserveViewModel.State.DATA_LOADED -> {
                        println(result.reserves)
                        reserveAdapter.setItems(result.reserves!!)
                    }
                    ReserveViewModel.State.LOADING_DATA -> {
                    }
                    ReserveViewModel.State.LOAD_ERROR -> {
                        Toasty.showErrorToasty(
                            requireContext(), getString(R.string.DatabaseError)
                        )
                        println(result.error)
                    }
                }
            }
        )
    }

    private fun subscribeAddReserve() {
        reserveViewModel.addReserveLiveData.observe(
            viewLifecycleOwner, { result ->
                when (result.state) {
                    ReserveViewModel.State.DATA_LOADED -> {
                        reserveViewModel.getReserves()
                    }
                    ReserveViewModel.State.LOADING_DATA -> {
                    }
                    ReserveViewModel.State.LOAD_ERROR -> {
                        Toasty.showErrorToasty(requireContext(), getString(R.string.DatabaseError))
                        println(result.error)
                    }
                }
            }
        )
    }

    private fun subscribeUpdateReserve() {
        reserveViewModel.updateReserveLiveData.observe(
            viewLifecycleOwner, { result ->
                when (result.state) {
                    ReserveViewModel.State.DATA_LOADED -> {
                        reserveViewModel.getReserves()
                    }
                    ReserveViewModel.State.LOADING_DATA -> {
                    }
                    ReserveViewModel.State.LOAD_ERROR -> {
                        Toasty.showErrorToasty(requireContext(), getString(R.string.DatabaseError))
                        println(result.error)
                    }
                }
            }
        )
    }

    private fun subscribeRemoveReserve() {

    }

    private fun setUpComponents() {
        setUpFab()
        reserveRecycleSetUp()
        setUpBottomSheet()
        setUpBottomSheetComponent()
        setUpBottomSheetSubmit()
        initBottomSheet()
    }

    private fun initBottomSheet() {
        date?.text = ""
        reserveRoom?.setText("")
        reserveCustomer?.setText("")
        reservePrice?.setText("")
    }

    private fun setUpBottomSheetComponent() {
        date = bottomSheetDialog?.findViewById(R.id.reserveDate)
        submit = bottomSheetDialog?.findViewById(R.id.reserveSubmit)
        reserveRoom = bottomSheetDialog?.findViewById(R.id.reserveRoom)
        reserveCustomer = bottomSheetDialog?.findViewById(R.id.reserveCustomer)
        reservePrice = bottomSheetDialog?.findViewById(R.id.reservePrice)
    }

    private var start: String? = null
    private var finish: String? = null

    private fun validateData(): ReserveModel? {
        val room = reserveRoom?.text.toString().trim()
        val customer = reserveCustomer?.text.toString().trim()
        val priceRoom = reservePrice?.text.toString().trim()

        if (validateInput(room, reserveRoom)) return null
        if (validateInput(customer, reserveCustomer)) return null
        if (validateInput(start, date)) return null
        if (validateInput(finish, date)) return null
        if (validateInput(priceRoom, reservePrice!!)) return null

        return ReserveModel(0, room, "مؤمن", customer, start!!, finish!!, priceRoom.toInt())
    }

    private fun validateInput(room: String?, et: EditText?): Boolean {
        room?.let {
            if (room.isEmpty()) {
                Toasty.showErrorToasty(requireContext(), getString(R.string.roomNumberError))
                et?.requestFocus()
                return true
            }
        }
        return false
    }

    private fun validateInput(room: String?, tv: TextView?): Boolean {
        room?.let {
            if (room.isEmpty()) {
                Toasty.showErrorToasty(requireContext(), getString(R.string.roomNumberError))
                tv?.requestFocus()
                return true
            }
        }
        return false
    }

    private fun setUpDatePicker() {
        val calendar = PersianCalendar(TimeZone.getDefault()).also {
            it.year = 1400                     // determines starting year
            it.month = Calendar.MONTH - 1          // determines starting month
            it.firstDayOfWeek =
                PersianCalendar.DEFAULT_FIRST_DAY_OF_WEEK  // sets first day of week to Monday
        }

        val datePicker = PrimeDatePicker.dialogWith(calendar)
            .pickRangeDays { startDay, endDay ->
                date?.text =
                    "از:  ".plus(startDay.longDateString.plus("\nتا:  ".plus(endDay.longDateString)))
                start = startDay.longDateString
                finish = endDay.longDateString
            }.build()
        datePicker.show(parentFragmentManager, "انتخاب تاریخ")
        start = ""
        finish = ""
        date?.text = ""
    }

    private fun setUpBottomSheetSubmit() {
        submit?.setOnClickListener {
            validateData()?.let { reserve ->
                reserveViewModel.addReserve(reserve)
                bottomSheetDialog?.dismiss()
            }
        }
        date?.setOnClickListener {
            setUpDatePicker()
        }
    }

    @SuppressLint("WrongConstant")
    private fun setUpBottomSheet() {
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog?.setContentView(R.layout.bottom_sheet_reserve)

        val layout =
            bottomSheetDialog?.findViewById<ScrollView>(R.id.bottomSheetReserve)
        layout?.layoutDirection = if (rtl) 1 else 0
    }

    private fun setUpFab() {
        mainFab.setOnClickListener {
            showBottomSheet(false)
        }
    }

    private fun showBottomSheet(update: Boolean = true) {
        this.update = update
        initBottomSheet()
        bottomSheetDialog?.show()
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
            when (it.itemId) {
                R.id.navClients -> {
                    val action = MainFragmentDirections.actionMainFragmentToClientsFragment()
                    requireView().findNavController().navigate(action)
                }
                R.id.navRoom -> {
                    val action = MainFragmentDirections.actionMainFragmentToRoomFragment()
                    requireView().findNavController().navigate(action)
                }
                R.id.navPassenger -> {
                    val action = MainFragmentDirections.actionMainFragmentToPassengerFragment()
                    requireView().findNavController().navigate(action)
                }
                R.id.navSetting -> {
                    val action = MainFragmentDirections.actionMainFragmentToSettingFragment()
                    requireView().findNavController().navigate(action)
                }
                R.id.navProfile -> {
                    val action = MainFragmentDirections.actionMainFragmentToProfileFragment()
                    requireView().findNavController().navigate(action)
                }
                R.id.navContactUs -> {
                    val action = MainFragmentDirections.actionMainFragmentToContactFragment()
                    requireView().findNavController().navigate(action)
                }
                R.id.navLogOut -> {
                    requireActivity().onBackPressed()
                }
            }
            drawer.closeDrawer(GravityCompat.START, false)
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
        setToolbarAnim()
        toolbar.title = ""
        val title: TextView = requireActivity().findViewById(R.id.toolbarTitle)
        title.text = "رزرو"
    }

    private fun setToolbarAnim() {
        val anim = TranslateAnimation(0.0F, 0.0F, -toolbar.height.toFloat(), 0.0F)
        anim.duration = 1000
        toolbar.animation = anim
        anim.start()
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                toolbar.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animation?) {
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })
    }

    private fun setUpBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            }
        )
    }
}