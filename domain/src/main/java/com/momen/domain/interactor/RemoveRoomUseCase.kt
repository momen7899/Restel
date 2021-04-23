package com.momen.domain.interactor

import com.momen.domain.executor.PostExecutionThread
import com.momen.domain.executor.ThreadExecutor
import com.momen.domain.interactor.type.UseCase
import com.momen.domain.model.Room
import com.momen.domain.repository.RoomRepository
import io.reactivex.Single
import javax.inject.Inject

class RemoveRoomUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase<Int, RemoveRoomUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(inputs: Params): Single<Int>? =
        roomRepository.removeRoom(inputs.id)


    class Params(val id: Int) {
        companion object {
            fun forRemoveRoom(id: Int) = Params(id)
        }
    }

}