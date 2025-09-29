package ru.urfu.droidpractice1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.urfu.droidpractice1.content.MainActivityScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityScreen()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("Lifecycle", "${this::class.simpleName} onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Lifecycle", "${this::class.simpleName} onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Lifecycle", "${this::class.simpleName} onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Lifecycle", "${this::class.simpleName} onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lifecycle", "${this::class.simpleName} onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("Lifecycle", "${this::class.simpleName} onRestart")
    }

}