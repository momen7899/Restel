package com.momen.restel.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.momen.domain.interactor.GetReservesUseCase
import com.momen.restel.reserve.model.ReserveModelDataMapper
import javax.inject.Inject

class MainReserveViewModelFactory @Inject constructor(
    private val reservesUseCase: GetReservesUseCase,
    private val reserveModelDataMapper: ReserveModelDataMapper
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainReserveViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainReserveViewModel(
                reservesUseCase,
                reserveModelDataMapper
            ) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class");
        }
    }

}
