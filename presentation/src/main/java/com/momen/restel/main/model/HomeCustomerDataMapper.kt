package com.momen.restel.main.model

import com.momen.domain.model.Customer
import javax.inject.Inject

class HomeCustomerDataMapper @Inject constructor() {
    fun transformCustomerToCustomerModels(data: ArrayList<Customer>?): ArrayList<HomeCustomerModel> {
        val list = ArrayList<HomeCustomerModel>()
        data?.let {
            data.forEach {
                list.add(transformCustomerToCustomerModel(it))
            }
        }
        return list
    }

    private fun transformCustomerToCustomerModel(customer: Customer): HomeCustomerModel =
        with(customer) {
            HomeCustomerModel(
                id,
                name,
                phoneNumber,
                nationalCode,
            )
        }

}
