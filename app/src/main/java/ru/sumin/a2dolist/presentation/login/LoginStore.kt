package ru.sumin.a2dolist.presentation.login

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import ru.sumin.a2dolist.presentation.login.LoginStore.Intent
import ru.sumin.a2dolist.presentation.login.LoginStore.Label
import ru.sumin.a2dolist.presentation.login.LoginStore.State

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

internal class LoginStoreFactory(
    private val storeFactory: StoreFactory
) {

    fun create(): LoginStore =
        object : LoginStore, Store<Intent, State, Label> by storeFactory.create(
            name = "LoginStore",
            initialState = State(),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
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

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                val isLoggedIn = false
                if (isLoggedIn) {
                    dispatch(Action.UserAlreadyLoggedIn)
                }
            }
        }
    }

    private class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
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
                try {
                    kotlinx.coroutines.delay(1000)
                    if (email == "test@test.com" && pass == "123456") {
                        dispatch(Msg.AuthSuccess)
                        publish(Label.LoginSuccess)
                    } else {
                        dispatch(Msg.ErrorOccurred("Incorrect email or password"))
                    }
                } catch (e: Exception) {
                    dispatch(Msg.ErrorOccurred(e.message ?: "Login failed"))
                }
            }
        }

        private fun handleRegistration(email: String, pass: String, confirmPass: String) {
            if (pass.length < 8) {
                dispatch(Msg.ErrorOccurred("Password must be at least 8 characters"))
                return
            }
            if (pass != confirmPass) {
                dispatch(Msg.ErrorOccurred("Passwords don't match"))
                return
            }

            scope.launch {
                try {
                    kotlinx.coroutines.delay(1000)
                    if (email == "test@test.com") {
                        dispatch(Msg.ErrorOccurred("This email is already taken"))
                    } else {
                        dispatch(Msg.AuthSuccess)
                        publish(Label.RegistrationSuccess)
                    }
                } catch (e: Exception) {
                    dispatch(Msg.ErrorOccurred(e.message ?: "Registration failed"))
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
