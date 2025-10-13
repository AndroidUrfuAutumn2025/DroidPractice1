package ru.urfu.droidpractice1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.urfu.droidpractice1.content.MainActivityScreen
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme


private const val TAG = "MainActivity"
class MainActivity : ComponentActivity() {

    private lateinit var secondArticleLauncher: ActivityResultLauncher<Intent>
    private var isSecondArticleReadState = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        if (savedInstanceState != null) {
            isSecondArticleReadState.value =
                savedInstanceState.getBoolean(KEY_SECOND_ARTICLE_READ, false)
        }

        secondArticleLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val wasRead = result.data?.getBooleanExtra(EXTRA_IS_READ, false) ?: false
                isSecondArticleReadState.value = wasRead
            }
        }

        setContent {
            DroidPractice1Theme {
                MainActivityScreen(
                    isSecondArticleRead = isSecondArticleReadState.value,
                    onNavigateToSecondArticle = {
                        val intent = Intent(this, SecondActivity::class.java).apply {
                            putExtra(
                                SecondActivity.EXTRA_READ_STATUS,
                                isSecondArticleReadState.value
                            )
                        }
                        secondArticleLauncher.launch(intent)
                    })
            }
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