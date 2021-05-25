package com.momen.domain.interactor

import com.momen.domain.executor.PostExecutionThread
import com.momen.domain.executor.ThreadExecutor
import com.momen.domain.interactor.type.UseCase
import com.momen.domain.model.Customer
import com.momen.domain.repository.HomeFeedRepository
import io.reactivex.Single
import javax.inject.Inject

class HomeGetCustomersUseCase @Inject constructor(
    private val repository: HomeFeedRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase<ArrayList<Customer>, HomeGetCustomersUseCase.Params>(
    threadExecutor,
    postExecutionThread
) {

    override fun buildUseCaseObservable(inputs: Params): Single<ArrayList<Customer>>? =
        repository.getCustomers()


    class Params() {
        companion object {
            fun forGetCustomers() = Params()
        }
    }

}