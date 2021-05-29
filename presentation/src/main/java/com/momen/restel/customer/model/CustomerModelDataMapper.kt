package com.momen.restel.customer.model

import android.content.Context
import com.momen.domain.model.Customer
import com.momen.restel.R
import javax.inject.Inject

class CustomerModelDataMapper @Inject constructor(private val context: Context) {
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
            return if (single) context.getString(R.string.single)
            else context.getString(R.string.married)
        }
        return ""
    }

    private fun transformGenderToString(gender: Boolean?): String {
        gender?.let {
            return if (gender) context.getString(R.string.male)
            else context.getString(R.string.female)
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
        gender == context.getString(R.string.male)


    private fun transformSingleToBoolean(single: String?): Boolean =
        single == context.getString(R.string.female)
}