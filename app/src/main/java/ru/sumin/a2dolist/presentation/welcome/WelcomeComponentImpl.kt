package ru.sumin.a2dolist.presentation.welcome

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import ru.sumin.a2dolist.presentation.extensions.componentScope

class WelcomeComponentImpl @AssistedInject constructor(
    private val welcomeStoreFactory: WelcomeStoreFactory,
    @Assisted("onGoClicked") private val onGoClicked: () -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext
) : WelcomeComponent, ComponentContext by componentContext{

    private val store = instanceKeeper.getStore {  welcomeStoreFactory.create() }

    init {
        componentScope().launch {
            store.labels.collect{
                when(it){
                    WelcomeStore.Label.ClickGo -> onGoClicked()
                }
            }
        }
    }

    override fun onClickGo() {
        store.accept(WelcomeStore.Intent.ClickGo)
    }

    @AssistedFactory
    interface Factory{
        fun create(
            @Assisted("onGoClicked") onGoClicked: () -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext
        ): WelcomeComponentImpl
    }

}