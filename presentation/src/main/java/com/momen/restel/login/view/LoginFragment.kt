package com.momen.restel.login.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.momen.data.executor.JobExecutor
import com.momen.data.mapper.UserEntityDataMapper
import com.momen.data.repository.UserRepositoryImpl
import com.momen.data.repository.user.RoomUserDatabase
import com.momen.data.repository.user.UserDataSourceFactory
import com.momen.data.room.FakeData
import com.momen.data.room.RoomInstance
import com.momen.domain.interactor.ValidUserUseCase
import com.momen.restel.R
import com.momen.restel.UiThread
import com.momen.restel.login.viewmodel.LoginViewModel
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_login.*

@Suppress("DEPRECATION")
class LoginFragment : Fragment() {

//    @Inject
//    lateinit var loginViewModelFactory: LoginViewModelFactory

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

        loginBtn.setOnClickListener {
            val userName = loginUserName.text.toString().trim()
            val password = loginPassword.text.toString().trim()

            if (userName.isEmpty()) {
                showErrorToasty(getString(R.string.errorLoginUsername))
                loginUserName.requestFocus()
                return@setOnClickListener
            } else if (password.isEmpty()) {
                showErrorToasty(getString(R.string.errorLoginPassword))
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
                        if (result.boolean!!) {
                            val action = LoginFragmentDirections.actionLoginFragmentToMainFragment()
                            view.findNavController().navigate(action)
                            showSuccessToasty(getString(R.string.successLogin))
                        } else {
                            showErrorToasty(getString(R.string.unsuccessLogin))
                        }
                    }
                    LoginViewModel.State.LOADING_DATA -> {
                    }
                    LoginViewModel.State.LOAD_ERROR -> {
                        showErrorToasty(getString(R.string.DatabaseError))
                        println(result.error)
                    }
                }
            }
        )
    }


    private fun setUpViewModel() {
        val roomInstance = RoomInstance(requireContext())
        val roomUserDatabase = RoomUserDatabase(roomInstance)
        val userDataSourceFactory = UserDataSourceFactory(roomUserDatabase)
        val userEntityDataMapper = UserEntityDataMapper()
        val userRepository = UserRepositoryImpl(userDataSourceFactory, userEntityDataMapper)
        val useCase = ValidUserUseCase(userRepository, JobExecutor(), UiThread())
        loginViewModel = LoginViewModel(useCase)


        val fakeData = FakeData(roomInstance)
        fakeData.addUser()
    }

    private fun injectViewModel() {
//        DaggerLoginComponent.builder()
//            .appComponent(App().appComponent)
//            .build()
//            .inject(this)
    }

    private fun showErrorToasty(msg: String) {
        Toasty.error(requireContext(), msg, Toast.LENGTH_SHORT, false).show()
    }

    private fun showSuccessToasty(msg: String) {
        Toasty.success(requireContext(), msg, Toast.LENGTH_SHORT, false).show()
    }

    private fun showWarningToasty(msg: String) {
        Toasty.warning(requireContext(), msg, Toast.LENGTH_SHORT, false).show()
    }

}