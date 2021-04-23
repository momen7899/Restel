package com.momen.domain.interactor

import com.momen.domain.executor.PostExecutionThread
import com.momen.domain.executor.ThreadExecutor
import com.momen.domain.interactor.type.UseCase
import com.momen.domain.model.User
import com.momen.domain.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase<User, GetUserUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(inputs: Params): Single<User>? =
        userRepository.getUser(inputs.id)


    class Params(val id: Int) {
        companion object {
            fun forGetUser(id: Int) = Params(id)
        }
    }

}