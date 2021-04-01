package com.momen.domain.interactor

import com.momen.domain.executor.PostExecutionThread
import com.momen.domain.executor.ThreadExecutor
import com.momen.domain.interactor.type.UseCase
import com.momen.domain.model.User
import com.momen.domain.repository.SplashRepository
import io.reactivex.Single
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val splashRepository: SplashRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase<Long, AddUserUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(inputs: Params): Single<Long> =
        splashRepository.addUser(inputs.user)

    class Params(val user: User) {
        companion object {
            fun forAddUser(user: User) =
                Params(user)
        }
    }


}