package ru.sumin.a2dolist.presentation.login

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import ru.sumin.a2dolist.domain.usecase.CheckAuthStatusUseCase
import ru.sumin.a2dolist.domain.usecase.LoginUserUseCase
import ru.sumin.a2dolist.domain.usecase.RegisterUserUseCase
import ru.sumin.a2dolist.presentation.login.LoginStore.Intent
import ru.sumin.a2dolist.presentation.login.LoginStore.Label
import ru.sumin.a2dolist.presentation.login.LoginStore.State
import javax.inject.Inject

enum class LoginTab { SIGN_UP, REGISTER }

interface LoginStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data class EmailChanged(val email: String) : Intent
        data class PasswordChanged(val password: String) : Intent
        data class ConfirmPasswordChanged(val password: String) : Intent
        data class TabSelected(val tab: LoginTab) : Intent
        data object SubmitClicked : Intent
        data object ForgotPasswordClicked : Intent
        //data class SocialAuthClicked(val provider: SocialProvider) : Intent на будущее
    }

    data class State(
        val email: String = "",
        val password: String = "",
        val confirmPassword: String = "", // Поле для регистрации
        val selectedTab: LoginTab = LoginTab.SIGN_UP,
        val error: String? = null, // Текст ошибки
        val isLoading: Boolean = false // Для крутилки/блокировки кнопки
    )

    sealed interface Label {
        data object LoginSuccess : Label
        data object RegistrationSuccess : Label
        data object NavigateToForgotPassword : Label
    }
}

internal class LoginStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val loginUserUseCase: LoginUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val checkAuthStatusUseCase: CheckAuthStatusUseCase
) {

    fun create(): LoginStore =
        object : LoginStore, Store<Intent, State, Label> by storeFactory.create(
            name = "LoginStore",
            initialState = State(),
            bootstrapper = BootstrapperImpl(checkAuthStatusUseCase),
            executorFactory = { ExecutorImpl(loginUserUseCase, registerUserUseCase) },
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data object UserAlreadyLoggedIn : Action
    }

    private sealed interface Msg {
        data class EmailChanged(val email: String) : Msg
        data class PasswordChanged(val password: String) : Msg
        data class ConfirmPasswordChanged(val password: String) : Msg
        data class TabSelected(val tab: LoginTab) : Msg
        data object LoadingStarted : Msg
        data class ErrorOccurred(val error: String?) : Msg
        data object AuthSuccess : Msg
    }

    private class BootstrapperImpl(
        private val checkAuthStatusUseCase: CheckAuthStatusUseCase
    ) : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                if (checkAuthStatusUseCase()) {
                    dispatch(Action.UserAlreadyLoggedIn)
                }
            }
        }
    }

    private class ExecutorImpl(
        private val loginUserUseCase: LoginUserUseCase,
        private val registerUserUseCase: RegisterUserUseCase
    ) : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            // Блокируем новые действия, пока идет загрузка
            if (intent is Intent.SubmitClicked && getState().isLoading) {
                return
            }

            // Сбрасываем ошибку при любом вводе или смене вкладки
            if (intent !is Intent.SubmitClicked && getState().error != null) {
                dispatch(Msg.ErrorOccurred(null))
            }
            when(intent) {
                is Intent.ConfirmPasswordChanged -> dispatch(Msg.ConfirmPasswordChanged(intent.password))
                is Intent.EmailChanged -> dispatch(Msg.EmailChanged(intent.email))
                Intent.ForgotPasswordClicked -> publish(Label.NavigateToForgotPassword)
                is Intent.PasswordChanged -> dispatch(Msg.PasswordChanged(intent.password))
                is Intent.TabSelected -> dispatch(Msg.TabSelected(intent.tab))
                is Intent.SubmitClicked -> {
                    val state = getState()
                    dispatch(Msg.LoadingStarted)

                    if (state.selectedTab == LoginTab.SIGN_UP) {
                        handleLogin(state.email, state.password)
                    } else {
                        handleRegistration(state.email, state.password, state.confirmPassword)
                    }
                }
            }
        }

        private fun handleLogin(email: String, pass: String) {
            scope.launch {
                val result = loginUserUseCase(email, pass)

                if (result.isSuccess) {
                    dispatch(Msg.AuthSuccess)
                    publish(Label.LoginSuccess)
                } else {
                    dispatch(Msg.ErrorOccurred(result.exceptionOrNull()?.message ?: "Login failed"))
                }
            }
        }

        private fun handleRegistration(email: String, pass: String, confirmPass: String) {
            scope.launch {
                val result = registerUserUseCase(email, pass, confirmPass)

                if (result.isSuccess) {
                    dispatch(Msg.AuthSuccess)
                    publish(Label.RegistrationSuccess)
                } else {
                    dispatch(Msg.ErrorOccurred(result.exceptionOrNull()?.message ?: "Registration failed"))
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when(action) {
                Action.UserAlreadyLoggedIn -> publish(Label.LoginSuccess)
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                Msg.AuthSuccess -> copy(
                isLoading = false,
                error = null,
                email = "",
                password = "",
                confirmPassword = ""
                )
                is Msg.ConfirmPasswordChanged -> copy(confirmPassword = msg.password)
                is Msg.EmailChanged -> copy(email = msg.email)
                is Msg.ErrorOccurred -> copy(error = msg.error, isLoading = false)
                Msg.LoadingStarted -> copy(isLoading = true, error = null)
                is Msg.PasswordChanged -> copy(password = msg.password)
                is Msg.TabSelected -> copy(
                selectedTab = msg.tab,
                error = null,
                password = "",
                confirmPassword = ""
                )
            }
    }
}
