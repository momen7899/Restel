package com.momen.restel.login.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.momen.data.entity.UserEntity
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


    override fun onCreate(savedInstanceState: Bundle?) {
        injectViewModel()

        super.onCreate(savedInstanceState)


        setUpViewModel()

    }

    private fun setUpViewModel() {
        val roomInstance = RoomInstance(context!!)
        val roomUserDatabase = RoomUserDatabase(roomInstance)
        val userDataSourceFactory = UserDataSourceFactory(roomUserDatabase)
        val userEntityDataMapper = UserEntityDataMapper()
        val userRepository = UserRepositoryImpl(userDataSourceFactory, userEntityDataMapper)
        val useCase = ValidUserUseCase(userRepository, JobExecutor(), UiThread())
        loginViewModel = LoginViewModel(useCase)


        val fakeData = FakeData(roomInstance)

        fakeData.addUser(
            UserEntity(
                null,
                "Momen",
                "Hamaveisi",
                "1234567890",
                "989184394657",
                "admin1",
                "admin1",
                "admin1",
                "admin"
            )
        )
        fakeData.getUsers()
        loginViewModel.isValidUser("admin", "admin")
    }

    private fun injectViewModel() {
//        DaggerLoginComponent.builder()
//            .appComponent(App().appComponent)
//            .build()
//            .inject(this)
    }
}