package ru.urfu.droidpractice1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.saveable.rememberSaveable
import ru.urfu.droidpractice1.content.MainActivityScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityScreen()
        }
    }
}