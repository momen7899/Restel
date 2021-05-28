package com.momen.restel.client.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.momen.domain.interactor.AddUser1UseCase
import com.momen.domain.interactor.EditUserUseCase
import com.momen.domain.interactor.GetUsers1UseCase
import com.momen.domain.interactor.RemoveUserUseCase
import com.momen.restel.login.model.UserModelDataMapper
import javax.inject.Inject

class ClientViewModelFactory @Inject constructor(
    private val addUserUseCase: AddUser1UseCase,
    private val editUserUseCase: EditUserUseCase,
    private val usersUseCase: GetUsers1UseCase,
    private val removeUserUseCase: RemoveUserUseCase,
    private val userModelDataMapper: UserModelDataMapper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClientViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ClientViewModel(
                addUserUseCase,
                editUserUseCase,
                usersUseCase,
                removeUserUseCase,
                userModelDataMapper
            ) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
