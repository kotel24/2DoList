package ru.sumin.a2dolist.presentation.login

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.sumin.a2dolist.presentation.extensions.componentScope

class LoginComponentImpl @AssistedInject constructor(
    private val storeFactory: LoginStoreFactory,
    @Assisted("componentContext") componentContext: ComponentContext,
    @Assisted("onLoginSuccess") private val onLoginSuccess: () -> Unit,
    @Assisted("onRegistrationSuccess") private val onRegistrationSuccess: () -> Unit,
    @Assisted("onNavigateToForgotPassword") private val onNavigateToForgotPassword: () -> Unit
) : LoginComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        storeFactory.create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<LoginStore.State>
        get() = store.stateFlow

    init {
        componentScope().launch {
            store.labels.collect {
                when (it) {
                    LoginStore.Label.LoginSuccess -> onLoginSuccess()
                    LoginStore.Label.NavigateToForgotPassword -> onNavigateToForgotPassword()
                    LoginStore.Label.RegistrationSuccess -> onRegistrationSuccess()
                }
            }
        }
    }

    override fun onEmailChanged(email: String) {
        store.accept(LoginStore.Intent.EmailChanged(email))
    }

    override fun onPasswordChanged(password: String) {
        store.accept(LoginStore.Intent.PasswordChanged(password))
    }

    override fun onConfirmPasswordChanged(password: String) {
        store.accept(LoginStore.Intent.ConfirmPasswordChanged(password))
    }

    override fun onTabSelected(tab: LoginTab) {
        store.accept(LoginStore.Intent.TabSelected(tab))
    }

    override fun onSubmitClicked() {
        store.accept(LoginStore.Intent.SubmitClicked)
    }

    override fun onForgotPasswordClicked() {
        store.accept(LoginStore.Intent.ForgotPasswordClicked)
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,
            @Assisted("onLoginSuccess") onLoginSuccess: () -> Unit,
            @Assisted("onRegistrationSuccess") onRegistrationSuccess: () -> Unit,
            @Assisted("onNavigateToForgotPassword") onNavigateToForgotPassword: () -> Unit
        ): LoginComponentImpl
    }
}