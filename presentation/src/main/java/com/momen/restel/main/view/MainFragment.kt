package com.momen.restel.main.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.widget.addTextChangedListener
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aminography.primecalendar.persian.PersianCalendar
import com.aminography.primedatepicker.picker.PrimeDatePicker
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import com.momen.restel.MainActivity
import com.momen.restel.R
import com.momen.restel.Utils
import com.momen.restel.app.App
import com.momen.restel.app.RoomDbModule
import com.momen.restel.comm.Toasty
import com.momen.restel.main.di.DaggerMainComponent
import com.momen.restel.main.model.HomeCustomerModel
import com.momen.restel.main.model.HomeRoomModel
import com.momen.restel.main.model.ReserveModel
import com.momen.restel.main.viewmodel.HomeFeedViewModel
import com.momen.restel.main.viewmodel.HomeFeedViewModelFactory
import com.momen.restel.main.viewmodel.ReserveViewModel
import com.momen.restel.main.viewmodel.ReserveViewModelFactory
import kotlinx.android.synthetic.main.bottom_sheet_reserve.*
import kotlinx.android.synthetic.main.bottom_sheet_reserve.view.*
import kotlinx.android.synthetic.main.card_delete.*
import kotlinx.android.synthetic.main.card_input.view.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_reserve.*
import kotlinx.android.synthetic.main.fragment_room.*
import kotlinx.android.synthetic.main.header.view.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@SuppressLint("InflateParams")
class MainFragment : Fragment() {

    private val rtl = true

    @Inject
    lateinit var reserveViewModelFactory: ReserveViewModelFactory

    @Inject
    lateinit var homeFeedViewModelFactory: HomeFeedViewModelFactory

    private var homeFeedViewModel: HomeFeedViewModel? = null
    private var reserveViewModel: ReserveViewModel? = null

    private var customerView: View? = null
    private var customerRecycler: RecyclerView? = null
    private var customerDialog: Dialog? = null
    private var customerAdapter: HomeCustomerAdapter? = null
    private var customerSearch: EditText? = null
    private val customerList = ArrayList<HomeCustomerModel>()

    private var roomView: View? = null
    private var roomRecycler: RecyclerView? = null
    private var roomDialog: Dialog? = null
    private var roomAdapter: HomeRoomAdapter? = null
    private var reserveAdapter: ReserveAdapter? = null
    private var bottomSheetDialog: BottomSheetDialog? = null
    private var roomSearch: EditText? = null
    private val roomList = ArrayList<HomeRoomModel>()
    private var id: Int? = null

    private var update = false
    private var date: TextView? = null
    private var submit: Button? = null
    private var reserveRoom: TextView? = null
    private var reserveCustomer: TextView? = null
    private var reservePrice: EditText? = null
    private var customer: HomeCustomerModel? = null
    private var room: HomeRoomModel? = null

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
        homeFeedViewModel = ViewModelProvider(this, homeFeedViewModelFactory)
            .get(HomeFeedViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        setUpActivityComponent()
        getReserves()
    }

    private fun getReserves() {
        reserveViewModel?.getReserves()
    }

    private fun subscribeViewModel() {
        subscribeGetReserves()
        subscribeAddReserve()
        subscribeUpdateReserve()
        subscribeRemoveReserve()
        subscribeGetRooms()
        subscribeGetCustomers()
    }

    private fun subscribeGetReserves() {
        reserveViewModel?.reserveLiveData?.observe(
            viewLifecycleOwner, { result ->
                when (result.state) {
                    ReserveViewModel.State.DATA_LOADED -> {
                        println(result.reserves)
                        reserveAdapter?.setItems(result.reserves!!)
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
        reserveViewModel?.addReserveLiveData?.observe(
            viewLifecycleOwner, { result ->
                when (result.state) {
                    ReserveViewModel.State.DATA_LOADED -> {
                        reserveViewModel?.getReserves()
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
        reserveViewModel?.updateReserveLiveData?.observe(
            viewLifecycleOwner, { result ->
                when (result.state) {
                    ReserveViewModel.State.DATA_LOADED -> {
                        reserveViewModel?.getReserves()
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

    private fun subscribeGetRooms() {
        homeFeedViewModel?.getRoomsLiveData?.observe(viewLifecycleOwner, { result ->
            when (result.state) {
                HomeFeedViewModel.State.DATA_LOADED -> {
                    result.rooms?.let { roomAdapter?.setItems(it) }
                }
                HomeFeedViewModel.State.LOADING_DATA -> {
                }
                HomeFeedViewModel.State.LOAD_ERROR -> {
                    Toasty.showErrorToasty(
                        requireContext(), getString(R.string.DatabaseError)
                    )
                    println(result.error)
                }
            }
        })
    }

    private fun subscribeGetCustomers() {
        homeFeedViewModel?.getCustomerLiveData?.observe(viewLifecycleOwner, { result ->
            when (result.state) {
                HomeFeedViewModel.State.DATA_LOADED -> {
                    this.customerList.clear()
                    result.customers?.let { this.customerList.addAll(it) }
                    result.customers?.let { customerAdapter?.setItems(it) }
                }
                HomeFeedViewModel.State.LOADING_DATA -> {
                }
                HomeFeedViewModel.State.LOAD_ERROR -> {
                    Toasty.showErrorToasty(
                        requireContext(), getString(R.string.DatabaseError)
                    )
                    println(result.error)
                }
            }
        })
    }

    private fun setUpComponents() {
        setUpFab()
        reserveRecycleSetUp()
        setUpBottomSheet()
        setUpBackPressed()
        setUpBottomSheetComponent()
        setUpBottomSheetSubmit()
    }

    private fun initBottomSheet(reserve: ReserveModel?) {
        if (update)
            reserve?.let { fillBottomSheetData(it) }
        else
            clearBottomSheetData()
    }

    @SuppressLint("SetTextI18n")
    private fun fillBottomSheetData(reserve: ReserveModel) {
        date?.text = "${reserve.startDate} \n${reserve.finishData}"
        reserveRoom?.text = reserve.room
        reserveCustomer?.text = reserve.customer
        reserve.price?.let { reservePrice?.setText(it) }
        submit?.text = getString(R.string.edit)
    }

    private fun clearBottomSheetData() {
        date?.text = ""
        reserveRoom?.text = ""
        reserveCustomer?.text = ""
        reservePrice?.setText("")
        submit?.text = getString(R.string.submit)
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
        val userName = "${Utils.getUser()?.firstName} ${Utils.getUser()?.lastName}"

        return if (update)
            ReserveModel(
                this.id,
                this.room?.name.toString(),
                userName,
                this.customer?.name.toString(),
                start!!,
                finish!!,
                priceRoom.toInt()
            )
        else ReserveModel(
            reserveAdapter?.nextId(),
            this.room?.name.toString(),
            userName,
            this.customer?.name.toString(),
            start!!,
            finish!!,
            priceRoom.toInt()
        )
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
                if (update)
                    reserveViewModel?.updateReserve(reserve)
                else
                    reserveViewModel?.addReserve(reserve)
                bottomSheetDialog?.dismiss()
            }
        }
        date?.setOnClickListener {
            setUpDatePicker()
        }
        reserveRoom?.setOnClickListener {
            setUpRoomDialogComponents()
        }
        reserveCustomer?.setOnClickListener {
            setUpCustomerDialogComponents()
        }
    }

    private fun setUpRoomDialogComponents() {
        homeFeedViewModel?.getRooms()
        setUpRoomDialog()
        setUpRoomDialogRecycle()
        setUpRoomDialogSearch()
    }

    private fun setUpRoomDialog() {
        roomView = requireActivity().layoutInflater
            .inflate(R.layout.dialog_main_reserve_room, null)
        roomView?.findViewById<LinearLayout>(R.id.dialogMainRoom)?.layoutDirection =
            if (rtl) View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR
        roomDialog = AlertDialog.Builder(requireContext())
            .setView(roomView).create()
        roomDialog?.show()
    }

    private fun setUpRoomDialogRecycle() {
        roomAdapter = HomeRoomAdapter(this)
        roomRecycler = roomView?.findViewById(R.id.mainCustomerRecycler)
        roomRecycler?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        roomRecycler?.adapter = roomAdapter
    }

    private fun setUpRoomDialogSearch() {
        roomSearch = customerView?.findViewById(R.id.searchMainReserveCustomer)
        roomSearch?.addTextChangedListener {
            roomAdapter?.filterItems(roomList, roomSearch?.text.toString().trim())
        }
    }

    private fun setUpCustomerDialogComponents() {
        homeFeedViewModel?.getCustomers()
        setUpCustomerDialog()
        setUpCustomerDialogRecycle()
        setUpCustomerDialogSearch()
    }

    private fun setUpCustomerDialog() {
        customerView = requireActivity().layoutInflater
            .inflate(R.layout.dialog_main_reserve_customer, null)

        customerView?.findViewById<LinearLayout>(R.id.dialogMainCustomer)?.layoutDirection =
            if (rtl) View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR

        customerDialog = AlertDialog.Builder(requireContext())
            .setView(customerView).create()
        customerDialog?.show()
    }

    private fun setUpCustomerDialogSearch() {
        customerSearch = customerView?.findViewById(R.id.searchMainReserveCustomer)
        customerSearch?.addTextChangedListener {
            customerAdapter?.filterItems(customerList, customerSearch?.text.toString().trim())
        }
    }

    private fun setUpCustomerDialogRecycle() {
        customerAdapter = HomeCustomerAdapter(this)
        customerRecycler = customerView?.findViewById(R.id.mainCustomerRecycler)
        customerRecycler?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        customerRecycler?.adapter = customerAdapter
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
            showBottomSheet(false, null)
        }
    }

    fun showBottomSheet(update: Boolean = true, reserve: ReserveModel?) {
        this.update = update
        id = reserve?.id
        initBottomSheet(reserve)
        bottomSheetDialog?.show()
    }

    private fun reserveRecycleSetUp() {
        reserveAdapter = ReserveAdapter(this)
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
        val user = Utils.getUser()
        user?.admin?.let {
            if (it == 0) navigationView.menu.findItem(R.id.navClients).isVisible = false
        }
        navigationView.headerUserName.text = user?.firstName.plus(" ${user?.lastName}")

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

    fun setCustomerSelected(customer: HomeCustomerModel) {
        this.customer = customer
        customerDialog?.dismiss()
        reserveCustomer?.text = this.customer?.name
    }

    fun setRoomSelected(room: HomeRoomModel) {
        this.room = room
        roomDialog?.dismiss()
        reserveRoom?.text = this.room?.name
    }

    fun showDelMsg(reserve: ReserveModel, position: Int) {
        showDelete()
        setUpDelete(reserve, position)
    }

    private fun setUpDelete(reserve: ReserveModel, position: Int) {
        var sec = 60
        val timer = Timer()
        var delete = true
        timer.schedule(object : TimerTask() {
            override fun run() {
                sec--
                timerCounter?.text = ((sec + 21) / 20).toString()
                timerProgressBar?.progress = sec
                if (sec == 0) {
                    this.cancel()
                    if (delete) {
                        reserveViewModel?.removeReserve(reserve)
                        delete = false
                    }
                }
            }
        }, 0, 50)

        undoRemove.setOnClickListener {
            delete = false
            reserveAdapter?.addItem(reserve, position)
            hideDelete()
        }
        timerProgressBar?.progress = sec
        timerCounter?.text = sec.toString()
    }

    private fun showDelete() {
        mainDelete.visibility = View.VISIBLE
        mainFab.visibility = View.GONE
    }

    private fun hideDelete() {
        mainDelete.visibility = View.GONE
        mainFab.visibility = View.VISIBLE
    }

}