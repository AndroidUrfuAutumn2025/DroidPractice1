package ru.urfu.droidpractice1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import ru.urfu.droidpractice1.content.MainActivityScreen

class MainActivity : ComponentActivity() {

    private var isSecondArticleRead = false

    // Лайки/дизлайки как MutableState
    var likeCount = mutableStateOf(0)
    var dislikeCount = mutableStateOf(0)

    private val secondActivityResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let { data ->
                isSecondArticleRead = data.getBooleanExtra(SecondActivity.EXTRA_READ_STATUS, false)
                Log.d("MainActivity", "Second article read status: $isSecondArticleRead")
                // Перерисовка Compose
                setContent {
                    MainActivityScreen(
                        isSecondArticleRead = isSecondArticleRead,
                        likeCount = likeCount,
                        dislikeCount = dislikeCount,
                        onNextArticleClick = { openSecondArticle() }
                    )
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate")

        // Восстанавливаем состояние лайков при перевороте экрана
        savedInstanceState?.let {
            likeCount.value = it.getInt("LIKE_COUNT", 0)
            dislikeCount.value = it.getInt("DISLIKE_COUNT", 0)
            isSecondArticleRead = it.getBoolean("READ_STATUS", false)
        }

        setContent {
            MainActivityScreen(
                isSecondArticleRead = isSecondArticleRead,
                likeCount = likeCount,
                dislikeCount = dislikeCount,
                onNextArticleClick = { openSecondArticle() }
            )
        }
    }

    private fun openSecondArticle() {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra(SecondActivity.EXTRA_READ_STATUS, isSecondArticleRead)
        secondActivityResult.launch(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("LIKE_COUNT", likeCount.value)
        outState.putInt("DISLIKE_COUNT", dislikeCount.value)
        outState.putBoolean("READ_STATUS", isSecondArticleRead)
        Log.d("MainActivity", "onSaveInstanceState")
    }
}
