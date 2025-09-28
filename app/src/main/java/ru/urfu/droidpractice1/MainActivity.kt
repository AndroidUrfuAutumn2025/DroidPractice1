package ru.urfu.droidpractice1

import android.content.Intent
import android.util.Log
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import ru.urfu.droidpractice1.content.MainActivityScreen
import androidx.activity.result.contract.ActivityResultContracts
import ru.urfu.droidpractice1.SecondActivity.Companion.KEY_READ

class MainActivity : ComponentActivity(), MainScreenHandler {
    private var count: Int by mutableIntStateOf(0)
    private var isRead: Boolean by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityScreen(this, isRead, count)
        }
    }

    override fun onToShareClicked() {
        Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, getString(R.string.main_article_header))
            startActivity(Intent.createChooser(this, "Поделиться"))
        }
    }

    override fun onLikeClicked() {
        count++
    }

    override fun onDislikeClicked() {
        if (count > 0) count--
    }

    override fun onSecondArticleClicked() {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra(IS_READ, isRead)
        resultLauncher.launch(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(IS_READ, isRead)
        outState.putInt(COUNT, count)
        Log.d("MainActivity", "onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        isRead = savedInstanceState.getBoolean(IS_READ)
        count = savedInstanceState.getInt(COUNT)
        Log.d("MainActivity", "onRestoreInstanceState")
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                isRead = result.data?.getBooleanExtra(KEY_READ, false) ?: false
            }
        }

    override fun onStart() {
        Log.d("MainActivity", "onStart")
        super.onStart()
    }

    override fun onRestart() {
        Log.d("MainActivity", "onRestart")
        super.onRestart()
    }

    override fun onResume() {
        Log.d("MainActivity", "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d("MainActivity", "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d("MainActivity", "onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d("MainActivity", "onDestroy")
        super.onDestroy()
    }

    companion object {
        const val IS_READ = "IS_READ"
        const val COUNT = "LIKES_COUNT"
    }
}