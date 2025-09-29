package ru.urfu.droidpractice1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.urfu.droidpractice1.content.MainActivityScreen

private const val TAG_MAIN = "MainActivityLifecycle"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG_MAIN, "onCreate")
        setContent {
            MainActivityScreen()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG_MAIN, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG_MAIN, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG_MAIN, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG_MAIN, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG_MAIN, "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG_MAIN, "onRestart")
    }
}
