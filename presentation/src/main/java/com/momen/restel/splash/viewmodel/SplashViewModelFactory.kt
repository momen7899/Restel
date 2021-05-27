package com.momen.restel.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.momen.domain.interactor.AddUserUseCase
import com.momen.domain.interactor.GetUsersUseCase
import com.momen.restel.login.model.UserModelDataMapper
import javax.inject.Inject

class SplashViewModelFactory @Inject constructor(
    private val addUserUseCase: AddUserUseCase,
    private val usersUseCase: GetUsersUseCase,
    private val userModelDataMapper: UserModelDataMapper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SplashViewModel(
                addUserUseCase,
                usersUseCase,
                userModelDataMapper
            ) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class");
        }
    }

}
