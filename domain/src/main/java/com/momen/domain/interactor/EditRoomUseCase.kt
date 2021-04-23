package com.momen.domain.interactor

import com.momen.domain.executor.PostExecutionThread
import com.momen.domain.executor.ThreadExecutor
import com.momen.domain.interactor.type.UseCase
import com.momen.domain.model.Room
import com.momen.domain.repository.RoomRepository
import io.reactivex.Single
import javax.inject.Inject

class EditRoomUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase<Room, EditRoomUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(inputs: Params): Single<Room>? =
        roomRepository.editRoom(inputs.room)


    class Params(val room: Room) {
        companion object {
            fun forEditRoom(room: Room) = Params(room)
        }
    }

}