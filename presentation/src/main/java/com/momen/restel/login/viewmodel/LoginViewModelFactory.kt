package com.momen.restel.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.momen.domain.interactor.AddUserUseCase
import com.momen.domain.interactor.ValidUserUseCase
import com.momen.restel.login.model.UserModelDataMapper
import javax.inject.Inject

class LoginViewModelFactory  constructor(
    private val validUserUseCase: ValidUserUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(
                validUserUseCase
            ) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class");
        }
    }

}
