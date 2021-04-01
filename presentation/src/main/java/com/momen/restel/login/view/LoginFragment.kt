package com.momen.restel.login.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.google.android.material.navigation.NavigationView
import com.momen.restel.R
import com.momen.restel.app.App
import com.momen.restel.app.RoomModule
import com.momen.restel.comm.Toasty
import com.momen.restel.login.di.DaggerLoginComponent
import com.momen.restel.login.viewmodel.LoginViewModel
import com.momen.restel.login.viewmodel.LoginViewModelFactory
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

@Suppress("DEPRECATION")
class LoginFragment : Fragment() {

    @Inject
    lateinit var loginViewModelFactory: LoginViewModelFactory

    private lateinit var loginViewModel: LoginViewModel

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

    }

    private fun hideActivityComponent() {
        hideNavMenu()
        hideToolbar()
    }

    private fun hideNavMenu() {
        requireActivity().findViewById<DrawerLayout>(R.id.mainDrawer)
            .setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        requireActivity().findViewById<NavigationView>(R.id.navView).visibility = View.GONE
    }

    private fun hideToolbar() {
        requireActivity().findViewById<Toolbar>(R.id.toolbar).visibility = View.GONE
    }


    private fun setUpComponents() {
        setUpHideKeyboard()
        hideActivityComponent()
        setUpLoginBtn()
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
            loginObserver(it)
        }
    }

    private fun loginObserver(view: View) {

        loginViewModel.loginLiveData.observe(
            viewLifecycleOwner, { result ->
                when (result.state) {
                    LoginViewModel.State.DATA_LOADED -> {
                        if (result.user?.id!! > 0) {
                            val action = LoginFragmentDirections.actionLoginFragmentToMainFragment()
                            view.findNavController().navigate(action)
                            Toasty.showSuccessToasty(
                                requireContext(),
                                getString(R.string.successLogin)
                            )
                        } else {
                            Toasty.showErrorToasty(
                                requireContext(),
                                getString(R.string.unsuccessLogin)
                            )
                        }
                    }
                    LoginViewModel.State.LOADING_DATA -> {
                    }
                    LoginViewModel.State.LOAD_ERROR -> {
                        Toasty.showErrorToasty(requireContext(), getString(R.string.DatabaseError))
                        println(result.error)
                    }
                }
            }
        )
    }


    private fun setUpViewModel() {
        loginViewModel = ViewModelProviders.of(this, loginViewModelFactory)
            .get(LoginViewModel::class.java)
    }

    private fun injectViewModel() {
        DaggerLoginComponent.builder()
            .appComponent(App().appComponent)
            .roomModule(RoomModule(requireContext()))
            .build()
            .inject(this)
    }

    private fun hideKeyboardFrom() {
        val imm: InputMethodManager =
            requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }


}