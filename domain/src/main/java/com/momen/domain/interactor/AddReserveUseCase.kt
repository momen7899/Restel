package com.momen.domain.interactor

import com.momen.domain.executor.PostExecutionThread
import com.momen.domain.executor.ThreadExecutor
import com.momen.domain.interactor.type.UseCase
import com.momen.domain.model.Reserve
import com.momen.domain.repository.ReserveRepository
import io.reactivex.Single
import javax.inject.Inject

class AddReserveUseCase @Inject constructor(
    private val reserveRepository: ReserveRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase<Long, AddReserveUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(inputs: Params): Single<Long>? =
        reserveRepository.addReserve(inputs.reserve)

    class Params(val reserve: Reserve) {
        companion object {
            fun forAddReserve(reserve: Reserve) = Params(reserve)
        }
    }


}