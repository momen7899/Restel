package com.momen.domain.interactor

import com.momen.domain.executor.PostExecutionThread
import com.momen.domain.executor.ThreadExecutor
import com.momen.domain.interactor.type.UseCase
import com.momen.domain.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class RemoveUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase<Int, RemoveUserUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(inputs: Params): Single<Int>? =
        userRepository.removeUser(inputs.id)


    class Params(val id: Int) {
        companion object {
            fun forRemoveUsers(id: Int) = Params(id)
        }
    }

}