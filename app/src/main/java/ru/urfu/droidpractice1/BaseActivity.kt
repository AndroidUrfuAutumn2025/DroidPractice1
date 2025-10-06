package ru.urfu.droidpractice1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

open class BaseActivity : ComponentActivity() {

    companion object {
        private const val TAG = "ActivityLifecycle"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "${this::class.java.simpleName} - onCreate()")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "${this::class.java.simpleName} - onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "${this::class.java.simpleName} - onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "${this::class.java.simpleName} - onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "${this::class.java.simpleName} - onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "${this::class.java.simpleName} - onRestart()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "${this::class.java.simpleName} - onDestroy()")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "${this::class.java.simpleName} - onSaveInstanceState()")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(TAG, "${this::class.java.simpleName} - onRestoreInstanceState()")
    }
}