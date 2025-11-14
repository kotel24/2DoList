package ru.sumin.a2dolist.presentation.welcome

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import ru.sumin.a2dolist.presentation.welcome.WelcomeStore.Intent
import ru.sumin.a2dolist.presentation.welcome.WelcomeStore.Label
import ru.sumin.a2dolist.presentation.welcome.WelcomeStore.State
import javax.inject.Inject

interface WelcomeStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object ClickGo: Intent
    }

    data class State(
        val todo: Unit = Unit
    )

    sealed interface Label {
        data object ClickGo: Label
    }
}

class WelcomeStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory
) {

    fun create(): WelcomeStore =
        object : WelcomeStore, Store<Intent, State, Label> by storeFactory.create(
            name = "WelcomeStore",
            initialState = State(),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
    }

    private sealed interface Msg {
    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.ClickGo -> publish(Label.ClickGo)
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = this
    }
}
