package ru.sumin.a2dolist.presentation.login

import kotlinx.coroutines.flow.StateFlow
import ru.sumin.a2dolist.presentation.login.LoginStore.State

interface LoginComponent {
    val model: StateFlow<State>
    fun onEmailChanged(email: String)
    fun onPasswordChanged(password: String)
    fun onConfirmPasswordChanged(password: String)
    fun onTabSelected(tab: LoginTab)
    fun onSubmitClicked()
    fun onForgotPasswordClicked()
}