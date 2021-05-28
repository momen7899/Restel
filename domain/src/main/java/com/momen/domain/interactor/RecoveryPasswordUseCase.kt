package com.momen.domain.interactor

import com.momen.domain.executor.PostExecutionThread
import com.momen.domain.executor.ThreadExecutor
import com.momen.domain.interactor.type.UseCase
import com.momen.domain.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class RecoveryPasswordUseCase @Inject constructor(
    private val userRepository: UserRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase<Int, RecoveryPasswordUseCase.Params>(threadExecutor, postExecutionThread) {


    override fun buildUseCaseObservable(inputs: Params): Single<Int>? =
        userRepository.recoveryPassword(
            inputs.userName, inputs.nationalCode, inputs.phoneNumber, inputs.password, inputs.md5
        )


    class Params constructor(
        val userName: String,
        val nationalCode: String,
        val phoneNumber: String,
        val password: String,
        val md5: String
    ) {
        companion object {
            fun forRecoveryPassword(
                userName: String,
                nationalCode: String,
                phoneNumber: String,
                password: String,
                md5: String
            ) =
                Params(userName, nationalCode, phoneNumber, password, md5)
        }
    }


}
