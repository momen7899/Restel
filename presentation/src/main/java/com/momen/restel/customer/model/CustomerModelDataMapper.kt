package com.momen.restel.customer.model

import com.momen.domain.model.Customer
import com.momen.restel.MainActivity
import com.momen.restel.R
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
            return if (single) MainActivity.instance().getString(R.string.single)
            else MainActivity.instance().getString(R.string.marry)
        }
        return ""
    }

    private fun transformGenderToString(gender: Boolean?): String {
        gender?.let {
            return if (gender) MainActivity.instance().getString(R.string.male)
            else MainActivity.instance().getString(R.string.female)
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

    private fun transformGenderToBoolean(gender: String?): Boolean =
        gender == MainActivity.instance().getString(R.string.male)


    private fun transformSingleToBoolean(single: String?): Boolean =
        single == MainActivity.instance().getString(R.string.female)
}