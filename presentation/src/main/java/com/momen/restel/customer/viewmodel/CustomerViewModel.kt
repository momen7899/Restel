package com.momen.restel.customer.viewmodel

import androidx.lifecycle.ViewModel
import com.momen.domain.interactor.*
import com.momen.restel.customer.model.CustomerModelDataMapper

class CustomerViewModel(
    private val addCustomerUseCase: AddCustomerUseCase,
    private val editCustomerUseCase: EditCustomerUseCase,
    private val customersUseCase: GetCustomersUseCase,
    private val removeCustomerUseCase: RemoveCustomerUseCase,
    private val customerModelDataMapper: CustomerModelDataMapper
) : ViewModel() {

}
