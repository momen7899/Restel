package com.momen.domain.interactor

import com.momen.domain.executor.PostExecutionThread
import com.momen.domain.executor.ThreadExecutor
import com.momen.domain.interactor.type.UseCase
import com.momen.domain.model.Reserve
import com.momen.domain.repository.ReserveRepository
import io.reactivex.Single
import javax.inject.Inject

class UpdateReserveUseCase @Inject constructor(
    private val reserveRepository: ReserveRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase<Int, UpdateReserveUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(inputs: Params): Single<Int>? =
        reserveRepository.updateReserve(inputs.reserve)

    class Params(val reserve: Reserve) {
        companion object {
            fun forUpdateReserve(reserve: Reserve) = Params(reserve)
        }
    }


}