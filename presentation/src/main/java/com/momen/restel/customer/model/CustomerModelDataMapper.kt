package com.momen.restel.customer.model

import com.momen.domain.model.Customer
import com.momen.domain.model.Room
import com.momen.restel.room.model.RoomModel
import javax.inject.Inject

class CustomerModelDataMapper @Inject constructor() {
    fun transformCustomerToCustomerModels(data: ArrayList<Customer>?): ArrayList<CustomerModel> {
        val list = ArrayList<CustomerModel>()
        data?.let {
            data.forEach {
                list.add(transformCustomerToCustomerModel(it))
            }
        }
        return list
    }
    private fun transformCustomerToCustomerModel(customer: Customer): CustomerModel =
        with(customer) { CustomerModel(id, name.toString(), phoneNumber.toString(), gender.toString(), married.toString()) }


    fun transformCustomerModelToCustomer(customer: CustomerModel): Customer =
        with(customer) { Customer(id, name, phoneNumber, nationalCode, gender, single) }
}