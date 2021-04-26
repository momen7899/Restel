package com.momen.restel.client.viewmodel

import androidx.lifecycle.ViewModel
import com.momen.domain.interactor.*
import com.momen.restel.login.model.UserModelDataMapper

class ClientViewModel(
    private val addUserUseCase: AddUser1UseCase,
    private val editUserUseCase: EditUserUseCase,
    private val usersUseCase: GetUsers1UseCase,
    private val userUseCase: GetUserUseCase,
    private val removeUserUseCase: RemoveUserUseCase,
    private val mapper: UserModelDataMapper
) : ViewModel() {

}
