package com.momen.restel.customer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.momen.domain.interactor.*
import com.momen.restel.customer.model.CustomerModel
import com.momen.restel.customer.model.CustomerModelDataMapper
import com.momen.restel.room.model.RoomModel
import com.momen.restel.room.viewmodel.RoomViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class CustomerViewModel(
    private val addCustomerUseCase: AddCustomerUseCase,
    private val editCustomerUseCase: EditCustomerUseCase,
    private val getCustomerUseCase: GetCustomersUseCase,
    private val removeCustomerUseCase: RemoveCustomerUseCase,
    private val customerModelDataMapper: CustomerModelDataMapper
) : ViewModel() {
    val addCustomerLiveData = MutableLiveData<CustomerViewModel.Result>()
    val editCustomerLiveData = MutableLiveData<CustomerViewModel.Result>()
    val getCustomerLiveData = MutableLiveData<CustomerViewModel.Result>()
    val removeCustomerLiveData = MutableLiveData<CustomerViewModel.Result>()
    private val disposables = CompositeDisposable()

    fun addCustomer(cutomer: CustomerModel) {
        var result: CustomerViewModel.Result?
        result = CustomerViewModel.Result(null, null, CustomerViewModel.State.LOADING_DATA, null)
        addCustomerLiveData.value = result


        val params = AddCustomerUseCase.Params.forAddCustomer(customerModelDataMapper.transformCustomerModelToCustomer(cutomer))
        val d: Disposable? = addCustomerUseCase.execute(params)?.subscribe({ res ->
            result = CustomerViewModel.Result(res, null, CustomerViewModel.State.DATA_LOADED, null)
            println(res)
            addCustomerLiveData.value = result
        }, { throwable ->
            result =
                CustomerViewModel.Result(null, null, CustomerViewModel.State.LOAD_ERROR, throwable.message)
            addCustomerLiveData.value = result
        }
        )
        d?.let { disposables.add(it) }
    }
    fun editCustomer(customer: CustomerModel) {
        var result: CustomerViewModel.Result?
        result = CustomerViewModel.Result(null, null, CustomerViewModel.State.LOADING_DATA, null)
        editCustomerLiveData.value = result


        val params = EditCustomerUseCase.Params.forEditCustomer(customerModelDataMapper.transformCustomerModelToCustomer(customer))
        val d: Disposable? = editCustomerUseCase.execute(params)?.subscribe({ res ->
            result = CustomerViewModel.Result(res.toLong(), null, CustomerViewModel.State.DATA_LOADED, null)
            editCustomerLiveData.value = result
            println(res)
        }, { throwable ->
            result =
                CustomerViewModel.Result(null, null, CustomerViewModel.State.LOAD_ERROR, throwable.message)
            editCustomerLiveData.value = result
        }
        )
        d?.let { disposables.add(it) }

    }

    fun getCustomers() {
        var result: CustomerViewModel.Result?
        result = CustomerViewModel.Result(null, null, CustomerViewModel.State.LOADING_DATA, null)
        getCustomerLiveData.value = result

        val params = GetCustomerUseCase.Params.forGetCustomer(customerModelDataMapper.transformCustomerModelToCustomer(customer))
        val d: Disposable? = getCustomerUseCase.execute(params)?.subscribe({ res ->
            result = CustomerViewModel.Result(
                null, customerModelDataMapper.transformCustomerToCustomerModels(res), CustomerViewModel.State.DATA_LOADED, null
            )
            getCustomerLiveData.value = result
        }, { throwable ->
            result =
                CustomerViewModel.Result(null, null, CustomerViewModel.State.LOAD_ERROR, throwable.message)
            getCustomerLiveData.value = result
        }
        )
        d?.let { disposables.add(it) }

    }

    fun removeCustomer(customer: CustomerModel) {
        var result: CustomerViewModel.Result?
        result = CustomerViewModel.Result(null, null, CustomerViewModel.State.LOADING_DATA, null)
        removeCustomerLiveData.postValue(result)

        val params = RemoveCustomerUseCase.Params.forRemoveCustomer(customerModelDataMapper.transformCustomerModelToCustomer(customer))
        val d: Disposable? = removeCustomerUseCase.execute(params)?.subscribe({ res ->
            result = CustomerViewModel.Result(res.toLong(), null, CustomerViewModel.State.DATA_LOADED, null)
            removeCustomerLiveData.value = result
        }, { throwable ->
            result =
                CustomerViewModel.Result(null, null, CustomerViewModel.State.LOAD_ERROR, throwable.message)
            removeCustomerLiveData.value = result
        }
        )
        d?.let { disposables.add(it) }
    }
    class Result(
        var response: Long?,
        var customers: ArrayList<CustomerModel>?,
        var state: State,
        var error: String?
    )

    enum class State {
        LOADING_DATA,
        DATA_LOADED,
        LOAD_ERROR
    }

}
