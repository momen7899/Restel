package com.momen.restel.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.momen.domain.interactor.HomeGetCustomersUseCase
import com.momen.domain.interactor.HomeGetRoomsUseCase
import com.momen.restel.main.model.HomeCustomerDataMapper
import com.momen.restel.main.model.HomeRoomDataMapper

class HomeFeedViewModelFactory(
    private val customersUseCase: HomeGetCustomersUseCase,
    private val roomsUseCase: HomeGetRoomsUseCase,
    private val customerDataMapper: HomeCustomerDataMapper,
    private val roomDataMapper: HomeRoomDataMapper
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeFeedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeFeedViewModel(
                 customersUseCase,
                 roomsUseCase,
                 customerDataMapper,
                 roomDataMapper
            ) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class");
        }
    }

}
