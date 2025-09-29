package ru.urfu.droidpractice1

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.urfu.droidpractice1.content.MainActivityScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        setContent {
            MainActivityScreen()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("LIFECYCLE", "${this.localClassName}: onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("LIFECYCLE", "${this.localClassName}: onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LIFECYCLE", "${this.localClassName}: onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("LIFECYCLE", "${this.localClassName}: onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LIFECYCLE", "${this.localClassName}: onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("LIFECYCLE", "${this.localClassName}: onRestart")
    }

}