package com.momen.domain.interactor

import com.momen.domain.executor.PostExecutionThread
import com.momen.domain.executor.ThreadExecutor
import com.momen.domain.interactor.type.UseCase
import com.momen.domain.model.Room
import com.momen.domain.repository.HomeFeedRepository
import io.reactivex.Single
import javax.inject.Inject

class HomeGetRoomsUseCase @Inject constructor(
    private val repository: HomeFeedRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase<ArrayList<Room>, HomeGetRoomsUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(inputs: Params): Single<ArrayList<Room>>? =
        repository.getRooms()

    class Params() {
        companion object {
            fun forGetRooms() = Params()
        }
    }
}