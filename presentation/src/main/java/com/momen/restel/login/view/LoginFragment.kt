package com.momen.restel.login.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import com.momen.restel.MainActivity
import com.momen.restel.R
import com.momen.restel.Utils
import com.momen.restel.app.App
import com.momen.restel.app.RoomDbModule
import com.momen.restel.comm.Toasty
import com.momen.restel.login.di.DaggerLoginComponent
import com.momen.restel.login.model.UserModel
import com.momen.restel.login.viewmodel.LoginViewModel
import com.momen.restel.login.viewmodel.LoginViewModelFactory
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

class LoginFragment : Fragment() {

    @Inject
    lateinit var loginViewModelFactory: LoginViewModelFactory

    private lateinit var loginViewModel: LoginViewModel

    private var bottomSheetDialog: BottomSheetDialog? = null
    private var userUserName: EditText? = null
    private var userNationalCode: EditText? = null
    private var userPhoneNumber: EditText? = null
    private var submit: Button? = null

    private var userName: String? = null
    private var userNational: String? = null
    private var userPhone: String? = null

    override
    fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_login, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {

        injectViewModel()

        super.onActivityCreated(savedInstanceState)

        setUpViewModel()
        setUpComponents()
        subscribeViewModels()
    }

    private fun subscribeViewModels() {
        loginObserver()
        recoveryPasswordSubscribe()
    }

    private fun hideActivityComponent() {
        hideNavMenu()
        hideToolbar()
    }

    private fun hideNavMenu() {
        requireActivity().findViewById<DrawerLayout>(R.id.mainDrawer)
            .setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        requireActivity().findViewById<NavigationView>(R.id.navView).visibility = View.GONE
    }

    private fun hideToolbar() {
        requireActivity().findViewById<Toolbar>(R.id.toolbar).visibility = View.GONE
    }

    private fun setUpComponents() {
        setUpHideKeyboard()
        hideActivityComponent()
        setUpRecoveryPassword()
        setUpLoginBtn()
        setUpBackPressed()
        setUpBottomSheet()
        setUpBottomSheetComponent()
        setUpBottomSheetSubmit()
    }

    private fun setUpRecoveryPassword() {
        recoveryPassword.setOnClickListener {
            bottomSheetDialog?.show()
        }
    }

    private fun setUpHideKeyboard() {
        loginFragment.setOnClickListener { hideKeyboardFrom() }
        loginConstraint.setOnClickListener { hideKeyboardFrom() }
    }

    private fun setUpLoginBtn() {

        loginBtn.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) hideKeyboardFrom()
        }

        loginBtn.setOnClickListener {
            hideKeyboardFrom()
            val userName = loginUserName.text.toString().trim()
            val password = loginPassword.text.toString().trim()

            if (userName.isEmpty()) {
                Toasty.showErrorToasty(requireContext(), getString(R.string.errorLoginUsername))
                loginUserName.requestFocus()
                return@setOnClickListener
            } else if (password.isEmpty()) {
                Toasty.showErrorToasty(requireContext(), getString(R.string.errorLoginPassword))
                loginPassword.requestFocus()
                return@setOnClickListener
            }
            loginViewModel.isValidUser(userName, password)
        }
    }

    private fun recoveryPasswordSubscribe() {
        loginViewModel.recoveryLiveData.observe(viewLifecycleOwner, {
            when (it.state) {
                LoginViewModel.State.DATA_LOADED -> {
                    Toasty.showSuccessToasty(
                        requireContext(),
                        getString(R.string.successRecoveryPassword)
                    )
                }
                LoginViewModel.State.LOADING_DATA -> {
                }
                LoginViewModel.State.LOAD_ERROR -> {
                    Toasty.showErrorToasty(
                        requireContext(),
                        getString(R.string.unsuccessTransaction)
                    )
                }
            }
        })
    }

    private fun loginObserver() {

        loginViewModel.loginLiveData.observe(
            viewLifecycleOwner, { result ->
                when (result.state) {
                    LoginViewModel.State.DATA_LOADED -> {
                        result.user?.let { loadMainFragment(it) }
                    }
                    LoginViewModel.State.LOADING_DATA -> {
                    }
                    LoginViewModel.State.LOAD_ERROR -> {
                        Toasty.showErrorToasty(
                            requireContext(),
                            getString(R.string.unsuccessLogin)
                        )
                    }
                }
            }
        )
    }

    private fun loadMainFragment(user: UserModel) {
        user.id?.let {
            if (it >= 0) {
                val navController = requireView().findNavController()
                val action = LoginFragmentDirections.actionLoginFragmentToMainFragment()
                if (navController.currentDestination?.id == R.id.loginFragment) {
                    navController.navigate(action)
                }
                Utils.setUser(user)
                Toasty.showSuccessToasty(
                    requireContext(),
                    getString(R.string.successLogin).plus(" ${user.firstName}")
                )
            } else {
                Toasty.showErrorToasty(
                    requireContext(),
                    getString(R.string.unsuccessLogin)
                )
            }
        }
    }

    private fun setUpViewModel() {
        loginViewModel =
            ViewModelProvider(this, loginViewModelFactory).get(LoginViewModel::class.java)
    }

    private fun injectViewModel() {
        DaggerLoginComponent.builder()
            .appComponent(App().appComponent)
            .roomDbModule(RoomDbModule(requireContext()))
            .build()
            .inject(this)
    }

    private fun hideKeyboardFrom() {
        val imm: InputMethodManager =
            requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
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

    private fun setUpBottomSheet() {
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog?.setContentView(R.layout.bottom_sheet_forget_pass)

        val layout = bottomSheetDialog?.findViewById<NestedScrollView>(R.id.bottomSheetForgetPass)
        layout?.layoutDirection = if (Utils.getRtl(MainActivity.instance())) View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR
    }

    private fun setUpBottomSheetComponent() {
        userUserName = bottomSheetDialog?.findViewById(R.id.userName)
        userNationalCode = bottomSheetDialog?.findViewById(R.id.userNationalCode)
        userPhoneNumber = bottomSheetDialog?.findViewById(R.id.userPhoneNumber)
        submit = bottomSheetDialog?.findViewById(R.id.submitBtn)
    }

    private fun setUpBottomSheetSubmit() {
        submit?.setOnClickListener {
            if (!validateData() || userName == null || userNational == null || userPhone == null) return@setOnClickListener

            loginViewModel.recoveryPassword(userName!!, userNational!!, userPhone!!)
            bottomSheetDialog?.dismiss()
        }
    }

    private fun validateData(): Boolean {
        userName = userUserName?.text.toString().trim()
        userNational = userNationalCode?.text.toString().trim()
        userPhone = userPhoneNumber?.text.toString().trim()

        if (validateInput(userName, userNationalCode)) return false
        if (validateInput(userNational, userNationalCode)) return false
        if (validateInput(userPhone, userPhoneNumber)) return false
        return true
    }

    private fun validateInput(str: String?, et: EditText?): Boolean {
        str?.let {
            if (str.isEmpty()) {
                Toasty.showErrorToasty(requireContext(), getString(R.string.emptyEditText))
                et?.requestFocus()
                return true
            }
        }
        return false
    }

}