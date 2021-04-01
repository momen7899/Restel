package com.momen.restel.splash.di

import com.momen.data.mapper.UserEntityDataMapper
import com.momen.data.repository.SplashRepositoryImpl
import com.momen.data.repository.splash.SplashDataSourceFactory
import com.momen.domain.interactor.AddUserUseCase
import com.momen.domain.interactor.GetUsersUseCase
import com.momen.domain.repository.SplashRepository
import com.momen.restel.login.model.UserModelDataMapper
import com.momen.restel.splash.viewmodel.SplashViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class SplashModule {

    @Provides
    internal fun provideSplashViewModelFactory(
        addUserUseCase: AddUserUseCase,
        getUsersUseCase: GetUsersUseCase,
        userModelDataMapper: UserModelDataMapper,
    ) = SplashViewModelFactory(addUserUseCase, getUsersUseCase, userModelDataMapper)


    @Provides
    internal fun provideSplashRepository(
        splashDataSourceFactory: SplashDataSourceFactory,
        userEntityDataMapper: UserEntityDataMapper
    ): SplashRepository = SplashRepositoryImpl(
        splashDataSourceFactory,
        userEntityDataMapper
    )


}
