package com.momen.domain.interactor

import com.momen.domain.executor.PostExecutionThread
import com.momen.domain.executor.ThreadExecutor
import com.momen.domain.interactor.type.UseCase
import com.momen.domain.model.User
import com.momen.domain.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class GetUsers1UseCase @Inject constructor(
    private val userRepository: UserRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase<ArrayList<User>, GetUsers1UseCase.Params>(
    threadExecutor,
    postExecutionThread
) {

    override fun buildUseCaseObservable(inputs: Params): Single<ArrayList<User>>? =
        userRepository.getUsers()


    class Params() {
        companion object {
            fun forGetUsers() = Params()
        }
    }

}