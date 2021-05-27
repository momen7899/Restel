package com.momen.domain.interactor

import com.momen.domain.executor.PostExecutionThread
import com.momen.domain.executor.ThreadExecutor
import com.momen.domain.interactor.type.UseCase
import com.momen.domain.model.Reserve
import com.momen.domain.repository.ReserveRepository
import io.reactivex.Single
import javax.inject.Inject

class GetReservesUseCase @Inject constructor(
    private val reserveRepository: ReserveRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase<ArrayList<Reserve>, GetReservesUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(inputs: Params): Single<ArrayList<Reserve>> =
        reserveRepository.getReserves()

    class Params {
        companion object {
            fun forGetReserve() = Params()
        }
    }

}