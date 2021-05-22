package com.momen.restel.customer.model

import com.momen.domain.model.Customer
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
        with(customer) {
            CustomerModel(
                id,
                name.toString(),
                phoneNumber.toString(),
                nationalCode,
                transformGenderToString(gender),
                transformSingleToString(single)
            )
        }

    private fun transformSingleToString(single: Boolean?): String? {
        single?.let {
            return if (single) "مجرد"
            else "متاهل"
        }
        return ""
    }

    private fun transformGenderToString(gender: Boolean?): String {
        gender?.let {
            return if (gender) "مرد"
            else "زن"
        }
        return ""
    }

    fun transformCustomerModelToCustomer(customer: CustomerModel): Customer =
        with(customer) {
            Customer(
                id,
                name,
                phoneNumber,
                nationalCode,
                transformGenderToBoolean(gender),
                transformSingleToBoolean(married)
            )
        }

    private fun transformGenderToBoolean(gender: String?): Boolean = gender == "مرد"


    private fun transformSingleToBoolean(single: String?): Boolean = single == "مجرد"
}