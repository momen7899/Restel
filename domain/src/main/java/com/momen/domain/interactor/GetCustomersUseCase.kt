package com.momen.domain.interactor

import com.momen.domain.executor.PostExecutionThread
import com.momen.domain.executor.ThreadExecutor
import com.momen.domain.interactor.type.UseCase
import com.momen.domain.model.Customer
import com.momen.domain.repository.CustomerRepository
import io.reactivex.Single
import javax.inject.Inject

class GetCustomersUseCase @Inject constructor(
    private val customerRepository: CustomerRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase<ArrayList<Customer>, GetCustomersUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(inputs: Params): Single<ArrayList<Customer>>? =
        customerRepository.getCustomers()


    class Params() {
        companion object {
            fun forGetCustomers() = Params()
        }
    }

}