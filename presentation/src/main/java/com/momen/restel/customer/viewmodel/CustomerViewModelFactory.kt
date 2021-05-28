package com.momen.restel.customer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.momen.domain.interactor.AddCustomerUseCase
import com.momen.domain.interactor.EditCustomerUseCase
import com.momen.domain.interactor.GetCustomersUseCase
import com.momen.domain.interactor.RemoveCustomerUseCase
import com.momen.restel.customer.model.CustomerModelDataMapper
import javax.inject.Inject

class CustomerViewModelFactory @Inject constructor(
    private val addCustomerUseCase: AddCustomerUseCase,
    private val editCustomerUseCase: EditCustomerUseCase,
    private val customersUseCase: GetCustomersUseCase,
    private val removeCustomerUseCase: RemoveCustomerUseCase,
    private val customerModelDataMapper: CustomerModelDataMapper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CustomerViewModel(
                addCustomerUseCase,
                editCustomerUseCase,
                customersUseCase,
                removeCustomerUseCase,
                customerModelDataMapper
            ) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}
