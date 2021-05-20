package com.momen.domain.interactor

import com.momen.domain.executor.PostExecutionThread
import com.momen.domain.executor.ThreadExecutor
import com.momen.domain.interactor.type.UseCase
import com.momen.domain.model.Room
import com.momen.domain.repository.RoomRepository
import io.reactivex.Single
import javax.inject.Inject

class GetRoomUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase<Room, GetRoomUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(inputs: Params): Single<Room>? =
        roomRepository.getRoom(inputs.id)

    class Params(val id: Int) {
        companion object {
            fun forGetRoom(id: Int) = Params(id)
        }
    }

}