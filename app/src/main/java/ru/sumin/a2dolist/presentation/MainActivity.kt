package ru.sumin.a2dolist.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ru.sumin.a2dolist.presentation.ui.theme._2DoListTheme
import ru.sumin.a2dolist.presentation.welcome.WelcomeContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _2DoListTheme {
            }
        }
    }
}