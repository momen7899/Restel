package com.momen.restel.client.di

import com.momen.data.mapper.UserEntityDataMapper
import com.momen.data.repository.datasource.user.UserDataSourceFactory
import com.momen.data.repository.impl.UserRepositoryImpl
import com.momen.domain.interactor.*
import com.momen.domain.repository.UserRepository
import com.momen.restel.client.viewmodel.ClientViewModelFactory
import com.momen.restel.login.model.UserModelDataMapper
import dagger.Module
import dagger.Provides

@Module
class ClientModule {
    @Provides
    internal fun provideClientViewModelFactory(
        addUserUseCase: AddUser1UseCase,
        editUserUseCase: EditUserUseCase,
        getUsersUseCase: GetUsers1UseCase,
        getUserUseCase: GetUserUseCase,
        removeUserUseCase: RemoveUserUseCase,
        userModelDataMapper: UserModelDataMapper
    ) = ClientViewModelFactory(
        addUserUseCase,
        editUserUseCase,
        getUsersUseCase,
        getUserUseCase,
        removeUserUseCase,
        userModelDataMapper
    )


    @Provides
    internal fun provideClientRepository(
        userDataSourceFactory: UserDataSourceFactory,
        userEntityDataMapper: UserEntityDataMapper
    ): UserRepository = UserRepositoryImpl(
        userDataSourceFactory,
        userEntityDataMapper
    )
}