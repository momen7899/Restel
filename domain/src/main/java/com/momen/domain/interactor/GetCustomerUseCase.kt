package com.momen.domain.interactor

import com.momen.domain.executor.PostExecutionThread
import com.momen.domain.executor.ThreadExecutor
import com.momen.domain.interactor.type.UseCase
import com.momen.domain.model.Customer
import com.momen.domain.repository.CustomerRepository
import io.reactivex.Single
import javax.inject.Inject

class GetCustomerUseCase @Inject constructor(
    private val customerRepository: CustomerRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase<Customer, GetCustomerUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(inputs: Params): Single<Customer>? =
        customerRepository.getCustomer(inputs.id)


    class Params(val id: Int) {
        companion object {
            fun forGetCustomer(id: Int) = Params(id)
        }
    }

}