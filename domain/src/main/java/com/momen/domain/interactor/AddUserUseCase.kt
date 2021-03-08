package com.momen.domain.interactor

import com.momen.domain.executor.PostExecutionThread
import com.momen.domain.executor.ThreadExecutor
import com.momen.domain.interactor.type.UseCase
import com.momen.domain.model.User
import com.momen.domain.repository.UserRepository
import io.reactivex.Single

class AddUserUseCase constructor(
    private val userRepository: UserRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase<Long, AddUserUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(inputs: Params): Single<Long> =
        userRepository.addUser(inputs.user)

    class Params private constructor(val user: User) {
        companion object {
            fun forAddUser(user: User) =
                Params(user)
        }
    }

}