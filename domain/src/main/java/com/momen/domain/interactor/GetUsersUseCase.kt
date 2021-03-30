package com.momen.domain.interactor

import com.momen.domain.executor.PostExecutionThread
import com.momen.domain.executor.ThreadExecutor
import com.momen.domain.interactor.type.UseCase
import com.momen.domain.model.User
import com.momen.domain.repository.SplashRepository
import io.reactivex.Single
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val splashRepository: SplashRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase<ArrayList<User>, GetUsersUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(inputs: Params): Single<ArrayList<User>> =
        splashRepository.getUsers()

    class Params {
        companion object {
            fun forGetUsers() = Params()
        }
    }

}