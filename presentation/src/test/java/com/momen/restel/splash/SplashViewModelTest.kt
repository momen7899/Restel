package com.momen.restel.splash

import androidx.lifecycle.viewmodel.compose.viewModel
import com.momen.data.executor.JobExecutor
import com.momen.domain.interactor.AddUserUseCase
import com.momen.domain.interactor.GetUsersUseCase
import com.momen.restel.RxImmediateSchedulerRule
import com.momen.restel.UiThread
import com.momen.restel.login.model.UserModelDataMapper
import com.momen.restel.splash.viewmodel.SplashViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.junit.MockitoJUnit

class SplashViewModelTest {

    @Rule
    @JvmField
    val rule = MockitoJUnit.rule()!!

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    private lateinit var splashViewModel: SplashViewModel
    private lateinit var useCase: GetUsersUseCase

    @Before
    fun setUp() {
        useCase = GetUsersUseCase(FakeSplashRepository(), JobExecutor(), UiThread())
        val addUseCase = AddUserUseCase(FakeSplashRepository(), JobExecutor(), UiThread())
        splashViewModel = SplashViewModel(addUseCase, useCase, UserModelDataMapper())
    }

    @Test
    fun `add user`() {
        splashViewModel?.
    }

    class GetUserViewModel()
}