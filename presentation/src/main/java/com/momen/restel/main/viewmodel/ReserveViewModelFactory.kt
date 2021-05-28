package com.momen.restel.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.momen.domain.interactor.AddReserveUseCase
import com.momen.domain.interactor.GetReservesUseCase
import com.momen.domain.interactor.RemoveReserveUseCase
import com.momen.domain.interactor.UpdateReserveUseCase
import com.momen.restel.main.model.ReserveModelDataMapper
import javax.inject.Inject

class ReserveViewModelFactory @Inject constructor(
    private val reservesUseCase: GetReservesUseCase,
    private val addReserveUseCase: AddReserveUseCase,
    private val updateUseCase: UpdateReserveUseCase,
    private val removeReserveUseCase: RemoveReserveUseCase,
    private val reserveModelDataMapper: ReserveModelDataMapper
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReserveViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReserveViewModel(
                reservesUseCase,
                addReserveUseCase,
                updateUseCase,
                removeReserveUseCase,
                reserveModelDataMapper
            ) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class");
        }
    }

}
