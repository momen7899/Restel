package com.momen.restel.customer.viewmodel

import androidx.lifecycle.ViewModel
import com.momen.domain.interactor.AddCustomerUseCase
import com.momen.domain.interactor.EditCustomerUseCase
import com.momen.domain.interactor.GetCustomerUseCase
import com.momen.domain.interactor.RemoveCustomerUseCase
import com.momen.restel.customer.model.CustomerModelDataMapper

class CustomerViewModel(
    private val addCustomerUseCase: AddCustomerUseCase,
    private val editCustomerUseCase: EditCustomerUseCase,
    private val customersUseCase: GetCustomerUseCase,
    private val customerUseCase: GetCustomerUseCase,
    private val removeCustomerUseCase: RemoveCustomerUseCase,
    private val customerModelDataMapper: CustomerModelDataMapper
) : ViewModel() {

}
