package ru.urfu.droidpractice1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import ru.urfu.droidpractice1.content.MainActivityScreen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {

    private var isReadSecondArticle by mutableStateOf(false)
    private var likeCount by mutableStateOf(0)
    private var dislikeCount by mutableStateOf(0)

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val isRead = result.data?.getBooleanExtra("second_article_read", false) ?: false
            isReadSecondArticle = isRead
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedInstanceState?.let {
            likeCount = it.getInt("likeCount", 0)
            dislikeCount = it.getInt("dislikeCount", 0)
        }

        setContent {
            MainActivityScreen(
                likeCount = likeCount,
                dislikeCount = dislikeCount,
                isReadSecondArticle = isReadSecondArticle,
                onLikeClick = { likeCount++ },
                onDislikeClick = { dislikeCount++ },
                onShareClick = { shareArticle() },
                onReadMoreClick = { openSecondArticle() }
            )
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("likeCount", likeCount)
        outState.putInt("dislikeCount", dislikeCount)
    }

    private fun shareArticle() {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, getString(R.string.first_article_title))
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_article)))
    }

    private fun openSecondArticle() {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("second_article_read", isReadSecondArticle)
        startForResult.launch(intent)
    }

}