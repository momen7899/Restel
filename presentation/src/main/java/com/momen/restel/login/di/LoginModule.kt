package com.momen.restel.login.di

import com.momen.data.mapper.UserEntityDataMapper
import com.momen.data.repository.datasource.user.UserDataSourceFactory
import com.momen.data.repository.impl.UserRepositoryImpl
import com.momen.domain.interactor.ValidUserUseCase
import com.momen.domain.repository.UserRepository
import com.momen.restel.login.model.UserModelDataMapper
import com.momen.restel.login.viewmodel.LoginViewModelFactory
import dagger.Module
import dagger.Provides


@Module
class LoginModule {

    @Provides
    internal fun provideLoginViewModelFactory(
        validUserUseCase: ValidUserUseCase,
        userModelDataMapper: UserModelDataMapper
    ) = LoginViewModelFactory(
        validUserUseCase,
        userModelDataMapper
    )


    @Provides
    internal fun provideLoginRepository(
        userDataSourceFactory: UserDataSourceFactory,
        userEntityDataMapper: UserEntityDataMapper
    ): UserRepository = UserRepositoryImpl(
        userDataSourceFactory,
        userEntityDataMapper
    )

}
