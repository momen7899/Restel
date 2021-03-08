package com.momen.domain.interactor

import com.momen.domain.executor.PostExecutionThread
import com.momen.domain.executor.ThreadExecutor
import com.momen.domain.interactor.type.UseCase
import com.momen.domain.repository.UserRepository
import io.reactivex.Single

class ValidUserUseCase(
    private val userRepository: UserRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase<Long, ValidUserUseCase.Params>(threadExecutor, postExecutionThread) {


    override fun buildUseCaseObservable(inputs: Params): Single<Long> =
        userRepository.isValidUser(inputs.userName, inputs.password, inputs.md5)


    class Params constructor(val userName: String, val password: String, val md5: String) {
        companion object {
            fun forIsValidUser(userName: String, password: String, md5: String) =
                Params(userName, password, md5)
        }
    }


}