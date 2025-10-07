package ru.urfu.droidpractice1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.urfu.droidpractice1.content.MainActivityScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Lifecycle", "${this.localClassName} - onCreate")
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityScreen()
        }
    }

    override fun onStart() {
        Log.d("Lifecycle", "${this.localClassName} - onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d("Lifecycle", "${this.localClassName} - onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d("Lifecycle", "${this.localClassName} - onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d("Lifecycle", "${this.localClassName} - onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d("Lifecycle", "${this.localClassName} - onDestroy")
        super.onDestroy()
    }
}