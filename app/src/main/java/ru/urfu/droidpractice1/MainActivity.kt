package ru.urfu.droidpractice1

import android.os.Bundle
import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import ru.urfu.droidpractice1.content.MainActivityScreen
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    private lateinit var secondArticleLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityScreen()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }

    companion object {
        const val REQUEST_CODE_SECOND_ACTIVITY = 1001
        const val EXTRA_IS_READ = "is_read"
        private const val KEY_SECOND_ARTICLE_READ = "second_article_read"
    }

}