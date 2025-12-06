package ru.urfu.droidpractice1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import ru.urfu.droidpractice1.content.MainActivityScreen

class MainActivity : ComponentActivity() {
    private var isSecondArticleRead = mutableStateOf(false)

    var likeCount = mutableIntStateOf(0)
    var dislikeCount = mutableIntStateOf(0)

    private val secondActivityResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let { data ->
                isSecondArticleRead.value = data
                    .getBooleanExtra(SecondActivity.ARTICLE_READ_STATUS, false)
                Log.d("MainActivity", "Second article was read: $isSecondArticleRead")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate")

        savedInstanceState?.let {
            likeCount.intValue = it.getInt(LIKE_COUNT, 0)
            dislikeCount.intValue = it.getInt(DISLIKE_COUNT, 0)
            isSecondArticleRead.value = it.getBoolean(ARTICLE_READ_STATUS, false)
        }

        setContent {
            MainActivityScreen(
                isSecondArticleRead = isSecondArticleRead.value,
                likeCount = likeCount,
                dislikeCount = dislikeCount,
                onNextArticleClick = { openSecondArticle() }
            )
        }
    }

    private fun openSecondArticle() {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra(SecondActivity.ARTICLE_READ_STATUS, isSecondArticleRead.value)
        secondActivityResult.launch(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(LIKE_COUNT, likeCount.intValue)
        outState.putInt(DISLIKE_COUNT, dislikeCount.intValue)
        outState.putBoolean(ARTICLE_READ_STATUS, isSecondArticleRead.value)
        Log.d("MainActivity", "onSaveInstanceState")
    }

    companion object {
        const val LIKE_COUNT = "LIKE_COUNT"
        const val DISLIKE_COUNT = "DISLIKE_COUNT"
        const val ARTICLE_READ_STATUS = "ARTICLE_READ_STATUS"
    }
}