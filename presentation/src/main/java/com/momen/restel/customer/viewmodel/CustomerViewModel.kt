package com.momen.restel.customer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.momen.domain.interactor.AddCustomerUseCase
import com.momen.domain.interactor.EditCustomerUseCase
import com.momen.domain.interactor.GetCustomersUseCase
import com.momen.domain.interactor.RemoveCustomerUseCase
import com.momen.restel.customer.model.CustomerModel
import com.momen.restel.customer.model.CustomerModelDataMapper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class CustomerViewModel(
    private val addCustomerUseCase: AddCustomerUseCase,
    private val editCustomerUseCase: EditCustomerUseCase,
    private val getCustomerUseCase: GetCustomersUseCase,
    private val removeCustomerUseCase: RemoveCustomerUseCase,
    private val customerModelDataMapper: CustomerModelDataMapper
) : ViewModel() {
    val addCustomerLiveData = MutableLiveData<Result>()
    val editCustomerLiveData = MutableLiveData<Result>()
    val getCustomerLiveData = MutableLiveData<Result>()
    val removeCustomerLiveData = MutableLiveData<Result>()
    private val disposables = CompositeDisposable()

    fun addCustomer(customer: CustomerModel) {
        var result: Result?
        result = Result(null, null, State.LOADING_DATA, null)
        addCustomerLiveData.value = result


        val params = AddCustomerUseCase.Params.forAddCustomer(
            customerModelDataMapper.transformCustomerModelToCustomer(customer)
        )
        val d: Disposable? = addCustomerUseCase.execute(params)?.subscribe({ res ->
            result = Result(res, null, State.DATA_LOADED, null)
            println(res)
            addCustomerLiveData.value = result
        }, { throwable ->
            result =
                Result(
                    null,
                    null,
                    State.LOAD_ERROR,
                    throwable.message
                )
            addCustomerLiveData.value = result
        }
        )
        d?.let { disposables.add(it) }
    }

    fun editCustomer(customer: CustomerModel) {
        var result: Result?
        result = Result(null, null, State.LOADING_DATA, null)
        editCustomerLiveData.value = result


        val params = EditCustomerUseCase.Params.forEditCustomer(
            customerModelDataMapper.transformCustomerModelToCustomer(customer)
        )
        val d: Disposable? = editCustomerUseCase.execute(params)?.subscribe({ res ->
            result = Result(
                res.toLong(),
                null,
                State.DATA_LOADED,
                null
            )
            editCustomerLiveData.value = result
            println(res)
        }, { throwable ->
            result =
                Result(
                    null,
                    null,
                    State.LOAD_ERROR,
                    throwable.message
                )
            editCustomerLiveData.value = result
        }
        )
        d?.let { disposables.add(it) }

    }

    fun getCustomers() {
        var result: Result?
        result = Result(null, null, State.LOADING_DATA, null)
        getCustomerLiveData.value = result

        val params = GetCustomersUseCase.Params.forGetCustomers()
        val d: Disposable? = getCustomerUseCase.execute(params)?.subscribe({ res ->
            result = Result(
                null,
                customerModelDataMapper.transformCustomerToCustomerModels(res),
                State.DATA_LOADED,
                null
            )
            println(result?.customers)
            getCustomerLiveData.value = result
        }, { throwable ->
            result =
                Result(
                    null,
                    null,
                    State.LOAD_ERROR,
                    throwable.message
                )
            getCustomerLiveData.value = result
        }
        )
        d?.let { disposables.add(it) }

    }

    fun removeCustomer(customer: CustomerModel) {
        var result: Result?
        result = Result(null, null, State.LOADING_DATA, null)
        removeCustomerLiveData.postValue(result)

        val params = RemoveCustomerUseCase.Params.forRemoveCustomer(
            customerModelDataMapper.transformCustomerModelToCustomer(customer)
        )
        val d: Disposable? = removeCustomerUseCase.execute(params)?.subscribe({ res ->
            result = Result(
                res.toLong(),
                null,
                State.DATA_LOADED,
                null
            )
            removeCustomerLiveData.value = result
        }, { throwable ->
            result =
                Result(
                    null,
                    null,
                    State.LOAD_ERROR,
                    throwable.message
                )
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
