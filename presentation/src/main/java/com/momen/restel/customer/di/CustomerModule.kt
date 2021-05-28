package com.momen.restel.customer.di

import com.momen.data.mapper.CustomerEntityDataMapper
import com.momen.data.repository.datasource.customer.CustomerDataSourceFactory
import com.momen.data.repository.impl.CustomerRepositoryImpl
import com.momen.domain.interactor.AddCustomerUseCase
import com.momen.domain.interactor.EditCustomerUseCase
import com.momen.domain.interactor.GetCustomersUseCase
import com.momen.domain.interactor.RemoveCustomerUseCase
import com.momen.domain.repository.CustomerRepository
import com.momen.restel.customer.model.CustomerModelDataMapper
import com.momen.restel.customer.viewmodel.CustomerViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class CustomerModule {

    @Provides
    internal fun provideCustomerViewModelFactory(
        addCustomerUseCase: AddCustomerUseCase,
        editCustomerUseCase: EditCustomerUseCase,
        getCustomersUseCase: GetCustomersUseCase,
        removeCustomerUseCase: RemoveCustomerUseCase,
        customerModelDataMapper: CustomerModelDataMapper
    ) = CustomerViewModelFactory(
        addCustomerUseCase,
        editCustomerUseCase,
        getCustomersUseCase,
        removeCustomerUseCase,
        customerModelDataMapper
    )

    @Provides
    internal fun provideCustomerRepository(
        customerDataSourceFactory: CustomerDataSourceFactory,
        customerEntityDataMapper: CustomerEntityDataMapper
    ): CustomerRepository = CustomerRepositoryImpl(
        customerDataSourceFactory,
        customerEntityDataMapper
    )
}