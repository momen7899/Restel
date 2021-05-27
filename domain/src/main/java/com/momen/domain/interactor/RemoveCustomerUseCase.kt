package com.momen.domain.interactor

import com.momen.domain.executor.PostExecutionThread
import com.momen.domain.executor.ThreadExecutor
import com.momen.domain.interactor.type.UseCase
import com.momen.domain.model.Customer
import com.momen.domain.repository.CustomerRepository
import io.reactivex.Single
import javax.inject.Inject

class RemoveCustomerUseCase @Inject constructor(
    private val customerRepository: CustomerRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase<Int, RemoveCustomerUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(inputs: Params): Single<Int>? =
        customerRepository.removeCustomer(inputs.customer)


    class Params(val customer: Customer) {
        companion object {
            fun forRemoveCustomer(customer: Customer) = Params(customer)
        }
    }

}